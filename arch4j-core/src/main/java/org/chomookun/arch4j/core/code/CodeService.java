package org.chomookun.arch4j.core.code;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.chomookun.arch4j.core.code.entity.CodeEntity;
import org.chomookun.arch4j.core.code.entity.CodeItemEntity;
import org.chomookun.arch4j.core.code.repository.CodeRepository;
import org.chomookun.arch4j.core.code.model.Code;
import org.chomookun.arch4j.core.code.model.CodeSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final CodeRepository codeRepository;

    /**
     * Saves code
     * @param code code
     * @return saved code
     */
    @Transactional
    public Code saveCode(Code code) {
        CodeEntity codeEntity = codeRepository.findById(code.getCodeId())
                .orElse(CodeEntity.builder()
                    .codeId(code.getCodeId())
                    .build());
        codeEntity.setName(code.getName());
        codeEntity.setNote(code.getNote());
        // code item (insert/update)
        AtomicInteger sort = new AtomicInteger();
        code.getCodeItems().forEach(codeItem -> {
            CodeItemEntity codeItemEntity = codeEntity.getCodeItems().stream()
                    .filter(item -> item.getItemId().equals(codeItem.getItemId()))
                    .findFirst()
                    .orElse(null);
            if(codeItemEntity == null) {
                codeItemEntity = CodeItemEntity.builder()
                        .codeId(codeEntity.getCodeId())
                        .itemId(codeItem.getItemId())
                        .build();
                codeEntity.getCodeItems().add(codeItemEntity);
            }
            codeItemEntity.setName(codeItem.getName());
            codeItemEntity.setSort(sort.getAndIncrement());
        });
        // code item (remove)
        codeEntity.getCodeItems().removeIf(codeItemEntity ->
                code.getCodeItems().stream()
                        .noneMatch(codeItem -> codeItem.getItemId().equals(codeItemEntity.getItemId())));
        // save
        CodeEntity savedCodeEntity = codeRepository.saveAndFlush(codeEntity);
        entityManager.refresh(savedCodeEntity);
        return Code.from(savedCodeEntity);
    }

    /**
     * Gets code
     * @param codeId code id
     * @return code
     */
    public Optional<Code> getCode(String codeId) {
        return codeRepository.findById(codeId)
                .map(Code::from);
    }

    /**
     * Deletes code
     * @param codeId code id
     */
    @Transactional
    public void deleteCode(String codeId) {
        codeRepository.deleteById(codeId);
        codeRepository.flush();
    }

    /**
     * Gets codes
     * @param codeSearch code search
     * @param pageable pageable
     * @return page of codes
     */
    public Page<Code> getCodes(CodeSearch codeSearch, Pageable pageable) {
        Page<CodeEntity> codeEntityPage = codeRepository.findAll(codeSearch, pageable);
        List<Code> codes = codeEntityPage.getContent().stream()
                .map(Code::from)
                .collect(Collectors.toList());
        return new PageImpl<>(codes, pageable, codeEntityPage.getTotalElements());
    }

}
