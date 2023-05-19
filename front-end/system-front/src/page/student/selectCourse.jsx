import React, { useEffect } from 'react'
import todoStore from '../../mobx/store'
import { useState } from 'react';
import { Divider, Radio, Table, Button } from 'antd';
import { PostApi } from './api'
import { toJS } from 'mobx';

export default function FindScore() {
  const [info, setInfo] = useState()
  const [selectCourse, setSelectCourse] = useState()
  const columns = [
    {
      title: '课程名',
      dataIndex: 'course_name'
    },
    {
      title: '考试类型',
      dataIndex: 'exam_type',
    },
    {
      title: '课程类型',
      dataIndex: 'course_attributes',
    },
    {
      title: '教师',
      dataIndex: 'tutor',
    },
    {
      title: '院系',
      dataIndex: 'department',
      render: (item) => {
        const arr = item?.split(';')
        return arr?.map((cur, index) => {
          return <div key={cur + index}>{cur}</div>
        })
      }
    },
    {
      title: '周次',
      dataIndex: 'week',
      render: (item) => {
        const arr = item?.split(';')
        return arr?.map((cur, index) => {
          return <div key={cur + index}>{cur}</div>
        })
      }
    },
    {
      title: '节次',
      dataIndex: 'weekly',
      render: (item) => {
        const arr = item?.split(';')
        return arr?.map((cur, index) => {
          return <div key={cur + index}>{cur}</div>
        })
      }
    },
    {
      title: '学分',
      dataIndex: 'credit',
    },
  ];
  const rowSelection = {
    onChange: (_, selectedRows) => {
      setSelectCourse(selectedRows)
    },
    getCheckboxProps: (record) => ({
      disabled: toJS(todoStore.shopCurt)?.findIndex(item => {
        return item.id === record.id
      }) !== -1, // Column configuration not to be checked
      name: record.name,
    }),
  };

  const changeShopCurt = () => {
    todoStore.changeShopCurt(selectCourse)
    setSelectCourse([])
  }

  useEffect(() => {
    const studentInfo = toJS(todoStore)
    console.log(studentInfo);
    PostApi('mongo/schedule/find_schedule_by_department', {
      department: studentInfo.studentInfo.department,
      studentId: studentInfo.studentInfo.unique_id,
      semesterId: studentInfo.semester_id
    }).then(
      res => {
        const curRes = res.data.data.schedule_info
        const curInfo = curRes.map(item => {
          return {
            course_name: item.course_name,
            exam_type: item.exam_type,
            course_attributes: item.course_attributes,
            tutor: item.tutor,
            department: item.department,
            week: item.week,
            weekly: item.weekly,
            credit: item.credit,
            course_id: item.course_id,
            id: item.id,
            course_number: item.course_number
          }
        })
        setInfo(curInfo)
      }
    )
  }, [])

  return (
    <>
      {!todoStore.can_select_class && <div>现在不是选课时间!</div>}
      {
        todoStore.can_select_class &&
        <div>
          <Divider />
          <Table
            rowSelection={{
              ...rowSelection,
            }}
            columns={columns}
            dataSource={info}
            rowKey={(record) => record.course_id}
          />
        </div>
      }
      <Button onClick={changeShopCurt} type='primary'>添加购物车</Button>
    </>
  )
}
