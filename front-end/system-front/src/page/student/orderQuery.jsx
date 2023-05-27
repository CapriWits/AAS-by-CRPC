import React, { useEffect } from 'react'
import todoStore from '../../mobx/store'
import { useState } from 'react';
import { Divider, Radio, Table, Button, Space } from 'antd';
import { PostApi } from './api'
import { toJS } from 'mobx';

export default function OrderQuery() {
  const [info, setInfo] = useState()
  const columns = [
    {
      title: '课程名',
      dataIndex: 'course_name'
    },
    {
      title: '课程号',
      dataIndex: 'course_id',
    },
    {
      title: '课序号',
      dataIndex: 'course_number',
    },
    {
      title: '学分',
      dataIndex: 'credit',
    }
  ];
  useEffect(() => {
    PostApi('order/find_order_by_studentId', {
      student_id: toJS(todoStore.studentInfo.unique_id)
    }).then(res => {
      const cur = res.data.data.orders
      const curRes = []
      cur.forEach(item => {
        const classInfo = JSON.parse(item.classes)
        classInfo.forEach(item => {
          console.log(item);
          return curRes.push({
            course_name: item.course_name,
            course_number: item.course_number,
            course_id: item.course_id,
            credit: item.credit
          })
        })
      })
      console.log(curRes);
      setInfo(curRes)
    })
  }, [])

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
    </>
  )
}
