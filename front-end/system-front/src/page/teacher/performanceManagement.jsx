import React, { useEffect, useState } from 'react'
import { PostApi } from './api'
import todoStore from '../../mobx/store'
import { Table, Button } from 'antd'
import { toJS } from 'mobx'
import { useNavigate } from 'react-router'

export default function PerformanceManagement() {
  const [info, setInfo] = useState()
  const navigate = useNavigate()
  useEffect(() => {
    const newStore = toJS(todoStore.teacherInfo)
    PostApi('class_schedule/find_by_tutorId',
      { tutor_id: newStore.unique_id }
    ).then(res => {
      console.log(res);
      setInfo(res.data.data.class_schedules)
    })
  }, [])
  const dataSource = info?.map((item, index) => {
    return {
      key: index,
      course_number: item.courseNum,
      course_id: item.courseId,
      course_name: item.courseName
    }
  })

  const navigateTo = (record) => {
    navigate('/home/teacher-student-info', {
      state: {
        studentId: record
      }
    })
  }

  const columns = [
    {
      title: '课程名',
      dataIndex: 'course_name',
      key: 'course_name',
    },
    {
      title: '课程号',
      dataIndex: 'course_id',
      key: 'course_id',
    },
    {
      title: '课序号',
      dataIndex: 'course_number',
      key: 'course_num'
    },
    {
      title: '操作',
      key: 'action',
      render: (record) => {
        return <Button type='primary' onClick={() => navigateTo(record)}>查看学生信息</Button>
      }
    }
  ];

  return (
    <>
      <Table dataSource={dataSource} columns={columns} />
    </>
  )
}
