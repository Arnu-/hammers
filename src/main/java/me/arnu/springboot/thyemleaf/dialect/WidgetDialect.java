package me.arnu.springboot.thyemleaf.dialect;

import me.arnu.springboot.thyemleaf.dialect.processor.*;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

@Component
public class WidgetDialect extends AbstractProcessorDialect {
    /**
     * 定义方言名称
     */
    private static final String NAME = "系统自定义标签";

    /**
     * 定义方言属性
     */
    private static final String PREFIX = "widget";

    protected WidgetDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        final Set<IProcessor> processor = new HashSet<>();
        //<Fw:select>标签
        processor.add(new SwitchCheckElementProcessor(PREFIX));
        processor.add(new ButtonAddElementProcessor(PREFIX));
        processor.add(new ButtonDAllElementProcessor(PREFIX));
        processor.add(new ButtonQueryElementProcessor(PREFIX));
        processor.add(new ButtonEditElementProcessor(PREFIX));
        processor.add(new ButtonDeleteElementProcessor(PREFIX));
        processor.add(new ButtonFunctionElementProcessor(PREFIX));
        processor.add(new ButtonSubmitElementProcessor(PREFIX));
        processor.add(new ButtonCollapseElementProcessor(PREFIX));
        processor.add(new ButtonExpandElementProcessor(PREFIX));

        processor.add(new SelectIdNameTreeElementProcessor(PREFIX));
        processor.add(new SelectIdNameSingleElementProcessor(PREFIX));
        processor.add(new SelectIdNameTabElementProcessor(PREFIX));
        processor.add(new SelectIdNameCheckBoxSingleElementProcessor(PREFIX));
        processor.add(new SelectIdNameRadioElementProcessor(PREFIX));

        processor.add(new CitySingleSelectElementProcessor(PREFIX));

        processor.add(new UploadSingleImageElementProcessor(PREFIX));
        processor.add(new UploadMultipleImageElementProcessor(PREFIX));

        processor.add(new TagsInputElementProcessor(PREFIX));
        processor.add(new DateSelectElementProcessor(PREFIX));
        processor.add(new IconPickerElementProcessor(PREFIX));
        return processor;
    }
}
