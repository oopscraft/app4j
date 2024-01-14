package org.oopscraft.arch4j.batch.item.file;

import lombok.Getter;
import lombok.Setter;
import org.oopscraft.arch4j.batch.item.file.transform.DelimitedLineTokenizer;
import org.oopscraft.arch4j.batch.item.file.transform.FieldConversionService;
import org.oopscraft.arch4j.batch.item.file.transform.ItemTypeDescriptor;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class DelimitedFileItemReader<T> extends GenericFileItemReader<T> {

    @Setter
    @Getter
    public String delimiter = String.valueOf(',');

    protected LineMapper<T> createLineMapper(Class<? extends T> itemType) {
        // item type descriptor
        ItemTypeDescriptor itemTypeDescriptor = new ItemTypeDescriptor(itemType);

        // conversion service
        FieldConversionService conversionService = FieldConversionService.builder()
                .dateTimeFormatter(dateTimeFormatter)
                .dateFormatter(dateFormatter)
                .timeFormatter(timeFormatter)
                .build();

        // line tokenizer
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(itemTypeDescriptor);
        lineTokenizer.setDelimiter(delimiter);

        // field set mapper
        BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(itemType);
        fieldSetMapper.setConversionService(conversionService);

        // line mapper
        DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        // return
        return lineMapper;
    }


}
