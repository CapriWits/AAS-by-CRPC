import React, { useEffect } from 'react'
import todoStore from '../../mobx/store'
import { useState } from 'react';
import { Divider, Radio, Table, Button, Space } from 'antd';
import { PostApi } from './api'
import { toJS } from 'mobx';

export default function ShopCurt() {
  const [info, setInfo] = useState(toJS(todoStore.shopCurt))
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
    {
      title: '操作',
      render: (record) => {
        return <Button danger onClick={() => [delCourse(record)]}>删除</Button>
      }
    }
  ];
  const summit = () => {
    const curInfo = toJS(todoStore)
    const parmas = {
      student_id: curInfo.studentInfo.unique_id,
      semester_id: curInfo.semester_id,
      classes: curInfo.shopCurt.map(item => {
        return {
          course_id: item.course_id,
          course_number: item.course_number,
          credit: item.credit
        }
      })
    }
    todoStore.changeShopCurt(selectCourse)
    setInfo([])
    todoStore.changeShopCurt([])
    PostApi('course/choose_course', parmas).then(res => {
      console.log(res);
    })
  }

  const delCourse = (record) => {
    const newCurtInfo = info.filter(item => {
      return item.id !== record.id
    })
    todoStore.changeShopCurt(newCurtInfo)
    setInfo(newCurtInfo)
  }
  return (
    <>
      {!todoStore.can_select_class && <div>现在不是选课时间!</div>}
      {
        todoStore.can_select_class &&
        <div>
          <Divider />
          <Table
            columns={columns}
            dataSource={info || []}
            rowKey={(record) => record}
          />
        </div>
      }
      <Space>
        <Button onClick={summit} type='primary'>提交</Button>
      </Space>
    </>
  )
}
