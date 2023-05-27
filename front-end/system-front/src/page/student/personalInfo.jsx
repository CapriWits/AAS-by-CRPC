import React, { useEffect, useState } from 'react'
import { Badge, Descriptions } from 'antd';
import { Post } from '../../postApi/index'
import todoStore from '../../mobx/store';
/**
* @author
* @function PersonalInfo
**/

const PersonalInfo = (props) => {
  const [info, setInfo] = useState()
  useEffect(() => {
    Post('student/get_student_info_with_filter', { filter: { id: todoStore.studentInfo.unique_id, name: null, department: null } }).then(res => {
      setInfo(res.data.data.student_info[0])
    })
  }, [])
  return (
    <Descriptions title="学生个人信息" bordered>
      <Descriptions.Item label="姓名">{info?.name}</Descriptions.Item>
      <Descriptions.Item label="学号">{info?.unique_id}</Descriptions.Item>
      <Descriptions.Item label="学分券">{info?.coupon}</Descriptions.Item>
      <Descriptions.Item label="身份证">{info?.id_card_num}</Descriptions.Item>
      <Descriptions.Item label="电话" span={2}>
        {info?.phone}
      </Descriptions.Item>
      <Descriptions.Item label="性别">
        {info?.gender}
      </Descriptions.Item>
      <Descriptions.Item label="民族">{info?.nationality}</Descriptions.Item>
      <Descriptions.Item label="生日">{info?.birthday}</Descriptions.Item>
      <Descriptions.Item label="高考生源">{info?.exam_from}</Descriptions.Item>
      <Descriptions.Item label="高考分数">
        {info?.gaokao_score}
      </Descriptions.Item>
      <Descriptions.Item label="毕业学校">
        {info?.graduated_school}
      </Descriptions.Item>
      <Descriptions.Item label="学院">
        {info?.department}
      </Descriptions.Item>
      <Descriptions.Item label="校区">
        {info?.campus}
      </Descriptions.Item>
      <Descriptions.Item label="班级">
        {info?.class}
      </Descriptions.Item>
      <Descriptions.Item label="培养层次">
        {info?.trainning_level}
      </Descriptions.Item>
    </Descriptions>
  )
}

export default PersonalInfo
