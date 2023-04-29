package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.controller.TutorManagementController.GetTutorInfoListParam;
import me.noctambulist.aasweb.repository.ITutorInfo;
import me.noctambulist.aasweb.model.TutorInfo;
import me.noctambulist.aasweb.model.dto.TutorInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/25 22:56
 */
@Service
public class TutorInfoService {

    private final ITutorInfo iTutorInfo;

    @Autowired
    public TutorInfoService(ITutorInfo iTutorInfo) {
        this.iTutorInfo = iTutorInfo;
    }

    @Transactional
    public TutorInfo create(TutorInfo tutorInfo) {
        return iTutorInfo.saveAndFlush(tutorInfo);
    }

    @Transactional
    public TutorInfo update(Long uniqueId, TutorInfo tutorInfo) {
        Optional<TutorInfo> optionalStudentInfo = iTutorInfo.findByUniqueId(uniqueId);
        if (optionalStudentInfo.isPresent()) {
            tutorInfo.setId(optionalStudentInfo.get().getId());
            return iTutorInfo.saveAndFlush(tutorInfo);
        } else {
            throw new CustomException(ResultEnum.TUTOR_NOT_EXISTS);
        }
    }

    public void deleteByUniqueId(Long id) {
        iTutorInfo.deleteByUniqueId(id);
    }

    public List<TutorInfoDTO> getTutorInfoWithFilter(GetTutorInfoListParam param) {
        GetTutorInfoListParam.GetTutorInfoFilter filter = param.getFilter();
        List<TutorInfo> tutorInfos = iTutorInfo.findAll(((root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (filter.getId() != null) {
                predicate.getExpressions().add(builder.equal(root.get("uniqueId"), filter.getId()));
            }
            if (!ObjectUtils.isEmpty(filter.getName())) {
                predicate.getExpressions().add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if (!ObjectUtils.isEmpty(filter.getDepartment())) {
                predicate.getExpressions()
                        .add(builder.like(root.get("department"), "%" + filter.getDepartment() + "%"));
            }
            return predicate;
        }));
        return tutorInfos.stream().map(TutorInfo::entity2DTO).collect(Collectors.toList());
    }
}
