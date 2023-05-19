import React, { useEffect, useState } from 'react'
import { PostApi } from './api'
import todoStore from '../../mobx/store'
import { Table, Button } from 'antd'
import { toJS } from 'mobx'

export default function WithdrawalFromClass() {
  const [info, setInfo] = useState()
  useEffect(() => {
    const newStore = toJS(todoStore)
    PostApi('class_schedule/find_by_studentId_semesterId',
      { student_id: newStore.studentInfo.unique_id, semester_id: newStore.semester_id }
    ).then(res => {
      console.log(res.data.data);
      setInfo(res.data.data.class_schedules)
    })
  }, [])
  const dataSource = info?.map((item, index) => {
    return {
      key: index,
      course_number: JSON.parse(item.class_info).course_number,
      credit: Number(JSON.parse(item.class_info).credit),
      course_id: item.course_id,
      course_name: JSON.parse(item.class_info).course_name,
      score: item.score.score
    }
  })
  // console.log(dataSource);

  const withdrawal = async (record) => {
    const curStore = toJS(todoStore)
    const parmas = {
      student_id: curStore.studentInfo.unique_id,
      semester_id: curStore.semester_id,
      course_id: record.course_id,
      course_number: record.course_number,
      credit: record.credit
    }
    console.log(parmas);
    await PostApi('course/drop_course', parmas)
    await PostApi('class_schedule/find_by_studentId_semesterId',
      { student_id: todoStore.studentInfo.unique_id, semester_id: todoStore.semester_id }
    ).then(res => {
      setInfo(res.data.data.class_schedules)
    })
  }

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
    },
    {
      title: '操作',
      key: 'action',
      render: (record) => {
        return <Button danger onClick={() => { withdrawal(record) }}>退课</Button>
      }
    }
  ];

  return (
    <>
      <Table dataSource={dataSource} columns={columns} />;
    </>
  )
}
