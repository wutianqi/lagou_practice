package com.lagou.import_useage;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wuqi
 * @date 2020-06-19 8:10
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.custom.impot.NotAutoConfig2"};
    }
}
