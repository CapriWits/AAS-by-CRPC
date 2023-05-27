import React, { useEffect, useState } from 'react'
import todoStore from '../../mobx/store'
import { PostApi } from './api'
import { Table } from 'antd'

export default function FindScore() {
  const [info, setInfo] = useState()
  useEffect(() => {
    PostApi('class_schedule/find_by_studentId_semesterId',
      { student_id: todoStore.studentInfo.unique_id, semester_id: todoStore.semester_id }
    ).then(res => {
      console.log(res.data.data.class_schedules);
      setInfo(res.data.data.class_schedules)
    })
  }, [])


  const dataSource = info?.map((item, index) => {
    return {
      key: index,
      course_name: JSON.parse(item.class_info).course_name,
      score: item.score.score
    }
  })

  const columns = [
    {
      title: '课程名',
      dataIndex: 'course_name',
      key: 'course_name',
    },
    {
      title: '分数',
      dataIndex: 'score',
      key: 'score',
    }
  ];

  return (
    <>
      <Table dataSource={dataSource} columns={columns} />
    </>
  )
}
