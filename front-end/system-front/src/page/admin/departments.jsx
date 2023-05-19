import React, { useState } from 'react'
import { useEffect } from 'react';
import DepartmentsTable from '../../comp/table/departmentsTable'
import { getCourseInfo, delCourseInfo, changeApi, getInfoApi } from './api'

export default function CourseInfo() {
  const TITLE_MAP = {
    course_id: '课程号',
    course_name: '课程名',
    course_number: '课序号',
    credit: '学分',
    course_attributes: '课程属性',
    exam_type: '考试类型',
    tutor: '导师',
    weekly: '周次',
    week: '星期',
    section_id: '节次',
    section_num: '节数',
    campus: '校区',
    building: '教学楼',
    class_room: '教室',
    department: '院系'
  }

  const TITLE_ARR = [
    'course_id',
    'course_number',
    'credit',
    'course_attributes',
    'exam_type',
    'tutor'
  ]

  // 需要特殊处理title数组
  const ARR_TITLE_ARR = [
    'weekly',
    'week',
    'section_id',
    'section_num',
    'building',
    'class_room',
    'department'
  ]

  const [data, setData] = useState()
  const [inputs, setInputs] = useState([]);

  const publishCourse = (info) => {
    console.log(info);
    changeApi('course_select/set_course_sku', info)
  }


  const setDepartments = (res) => {
    console.log(res);
    changeApi('course_select/set_course_selection_list', {"departments": res})
  }

  useEffect(() => {
    getCourseInfo('mongo/schedule/find_all').then(res => {
      setData(res.data.data.schedule_info)
    })
    getInfoApi('course_select/get_course_selection_list').then(res => {
      const cur = res.data.data.departments
      const arr = Object.keys(cur).map((item, index) => ({ id: index, value: cur[item] }))
      setInputs(arr)
    })
  }, [])

  const changeInputs = (info) => {
    setInputs(info)
  }


  return (
    <DepartmentsTable btnTitle='可选课院系设置' publishCourse={publishCourse} changeInputs={changeInputs} inputs={inputs} setDepartments={setDepartments} delCourseInfo={delCourseInfo} info={data} ARR_TITLE_ARR={ARR_TITLE_ARR} TITLE_MAP={TITLE_MAP} TITLE_ARR={TITLE_ARR} />
  )
}