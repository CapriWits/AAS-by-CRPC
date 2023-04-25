package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.controller.StudentManagementController.GetStudentInfoListParam;
import me.noctambulist.aasweb.controller.StudentManagementController.GetStudentInfoListParam.GetStudentInfoFilter;
import me.noctambulist.aasweb.entity.StudentInfo;
import me.noctambulist.aasweb.entity.dto.StudentInfoDTO;
import me.noctambulist.aasweb.repository.IStudentInfo;
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
 * @Date: 2023/4/23 11:59
 */
@Service
public class StudentInfoService {

    private final IStudentInfo iStudentInfo;

    @Autowired
    public StudentInfoService(IStudentInfo IStudentInfo) {
        this.iStudentInfo = IStudentInfo;
    }

    @Transactional
    public StudentInfo create(StudentInfo studentInfo) {
        return iStudentInfo.saveAndFlush(studentInfo);
    }

    @Transactional
    public StudentInfo update(Long uniqueId, StudentInfo studentInfo) {
        Optional<StudentInfo> optionalStudentInfo = iStudentInfo.findByUniqueId(uniqueId);
        if (optionalStudentInfo.isPresent()) {
            studentInfo.setId(optionalStudentInfo.get().getId());
            return iStudentInfo.saveAndFlush(studentInfo);
        } else {
            throw new CustomException(ResultEnum.STUDENT_NOT_EXISTS);
        }
    }

    public void deleteByUniqueId(Long id) {
        iStudentInfo.deleteByUniqueId(id);
    }

    public StudentInfo findByUniqueId(Long uniqueId) {
        return iStudentInfo.findByUniqueId(uniqueId).orElse(null);
    }

    public List<StudentInfoDTO> getStudentInfoWithFilter(GetStudentInfoListParam param) {
        GetStudentInfoFilter filter = param.getFilter();
        List<StudentInfo> studentInfos = iStudentInfo.findAll(((root, query, builder) -> {
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
        return studentInfos.stream().map(StudentInfo::entityToDTO).collect(Collectors.toList());
    }
}
