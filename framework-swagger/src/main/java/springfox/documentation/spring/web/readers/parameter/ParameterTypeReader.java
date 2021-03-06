/*
 *
 *  Copyright 2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.documentation.spring.web.readers.parameter;

import com.fasterxml.classmate.ResolvedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import static springfox.documentation.schema.Collections.collectionElementType;
import static springfox.documentation.schema.Collections.isContainerType;
import static springfox.documentation.spring.web.readers.parameter.ParameterTypeDeterminer.determineScalarParameterType;

/**
 * 基于2.9.2的同名类修改，从jar包中把这个类拿出来，编译时会使用这里的代码而非jar包中的class
 * 修复{@code findParameterType}方法会错设带注解的方法参数的ParameterType的bug
 * 详见 https://github.com/springfox/springfox/issues/2855
 *
 * @author SpringFox
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ParameterTypeReader implements ParameterBuilderPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(springfox.documentation.spring.web.readers.parameter.ParameterTypeReader.class);

    @Override
    public void apply(ParameterContext context) {
        context.parameterBuilder().parameterType(findParameterType(context));
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    /**
     * 原来的代码：
     * <pre>
     * public static String findParameterType(ParameterContext parameterContext) {
     *     ResolvedMethodParameter resolvedMethodParameter = parameterContext.resolvedMethodParameter();
     *     ResolvedType parameterType = resolvedMethodParameter.getParameterType();
     *     parameterType = parameterContext.alternateFor(parameterType);
     *
     *     // Multi-part file trumps any other annotations
     *     if (isFileType(parameterType) || isListOfFiles(parameterType)) {
     *       return "form";
     *     }
     *     if (resolvedMethodParameter.hasParameterAnnotation(PathVariable.class)) {
     *       return "path";
     *     } else if (resolvedMethodParameter.hasParameterAnnotation(RequestBody.class)) {
     *       return "body";
     *     } else if (resolvedMethodParameter.hasParameterAnnotation(RequestPart.class)) {
     *       return "formData";
     *     } else if (resolvedMethodParameter.hasParameterAnnotation(RequestParam.class)) {
     *       // 这里的代码和下面的重复了
     *       return determineScalarParameterType(
     *           parameterContext.getOperationContext().consumes(),
     *           parameterContext.getOperationContext().httpMethod());
     *     } else if (resolvedMethodParameter.hasParameterAnnotation(RequestHeader.class)) {
     *       return "header";
     *     } else if (resolvedMethodParameter.hasParameterAnnotation(ModelAttribute.class)) {
     *       LOGGER.warn("@ModelAttribute annotated parameters should have already been expanded via "
     *           + "the ExpandedParameterBuilderPlugin");
     *     }
     *     // 这个地方很扯淡，完全没考虑到其它注解的存在，且不说自定义注解，就连加个@NotNull都会把query变成body
     *     if (!resolvedMethodParameter.hasParameterAnnotations()) {
     *       return determineScalarParameterType(
     *           parameterContext.getOperationContext().consumes(),
     *           parameterContext.getOperationContext().httpMethod());
     *     }
     *     // 默认body可真有你的
     *     return "body";
     *   }
     * </pre>
     */
    public static String findParameterType(ParameterContext parameterContext) {
        ResolvedMethodParameter resolvedMethodParameter = parameterContext.resolvedMethodParameter();
        ResolvedType parameterType = resolvedMethodParameter.getParameterType();
        parameterType = parameterContext.alternateFor(parameterType);
        if (isFileType(parameterType) || isListOfFiles(parameterType)) {
            return "form";
        }
        if (resolvedMethodParameter.hasParameterAnnotation(PathVariable.class)) {
            return "path";
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestBody.class)) {
            return "body";
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestPart.class)) {
            return "formData";
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestHeader.class)) {
            return "header";
        } else if (resolvedMethodParameter.hasParameterAnnotation(ModelAttribute.class)) {
            LOGGER.warn("@ModelAttribute annotated parameters should have already been expanded via "
                    + "the ExpandedParameterBuilderPlugin");
        }
        return determineScalarParameterType(
                parameterContext.getOperationContext().consumes(),
                parameterContext.getOperationContext().httpMethod());
    }

    private static boolean isListOfFiles(ResolvedType parameterType) {
        return isContainerType(parameterType) && isFileType(collectionElementType(parameterType));
    }

    private static boolean isFileType(ResolvedType parameterType) {
        return MultipartFile.class.isAssignableFrom(parameterType.getErasedType());
    }
}
