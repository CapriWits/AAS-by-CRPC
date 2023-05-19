import React from 'react';
import { Table } from 'antd';

function StudentCourseList(props) {
  const columns = [
    {
      title: '',
      dataIndex: 'time',
      width: 80,
      align: 'center',
    },
    {
      title: '周一',
      dataIndex: 'Monday',
      width: '14.28%',
    },
    {
      title: '周二',
      dataIndex: 'Tuesday',
      width: '14.28%',
    },
    {
      title: '周三',
      dataIndex: 'Wednesday',
      width: '14.28%',
    },
    {
      title: '周四',
      dataIndex: 'Thursday',
      width: '14.28%',
    },
    {
      title: '周五',
      dataIndex: 'Friday',
      width: '14.28%',
    },
    {
      title: '周六',
      dataIndex: 'Saturday',
      width: '14.28%',
    },
    {
      title: '周日',
      dataIndex: 'Sunday',
      width: '14.28%',
    },
  ];
  const { info } = props
  const dataSource = [
    {
      key: '1',
      time: '1',
      Monday: '语文',
      Tuesday: '',
      Wednesday: '数学',
      Thursday: '英语',
      Friday: '物理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '2',
      time: '2',
      Monday: '生物',
      Tuesday: '',
      Wednesday: '政治',
      Thursday: '历史',
      Friday: '地理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '3',
      time: '3',
      Monday: '化学',
      Tuesday: '',
      Wednesday: '',
      Thursday: '',
      Friday: '',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '4',
      time: '4',
      Monday: '语文',
      Tuesday: '',
      Wednesday: '数学',
      Thursday: '英语',
      Friday: '物理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '5',
      time: '5',
      Monday: '生物',
      Tuesday: '',
      Wednesday: '政治',
      Thursday: '历史',
      Friday: '地理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '6',
      time: '6',
      Monday: '化学',
      Tuesday: '',
      Wednesday: '',
      Thursday: '',
      Friday: '',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '7',
      time: '7',
      Monday: '语文',
      Tuesday: '',
      Wednesday: '数学',
      Thursday: '英语',
      Friday: '物理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '8',
      time: '8',
      Monday: '生物',
      Tuesday: '',
      Wednesday: '政治',
      Thursday: '历史',
      Friday: '地理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '9',
      time: '9',
      Monday: '化学',
      Tuesday: '',
      Wednesday: '',
      Thursday: '',
      Friday: '',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '10',
      time: '10',
      Monday: '语文',
      Tuesday: '',
      Wednesday: '数学',
      Thursday: '英语',
      Friday: '物理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '11',
      time: '11',
      Monday: '生物',
      Tuesday: '',
      Wednesday: '政治',
      Thursday: '历史',
      Friday: '地理',
      Saturday: '',
      Sunday: '',
    },
    {
      key: '12',
      time: '12',
      Monday: '化学',
      Tuesday: '',
      Wednesday: '',
      Thursday: '',
      Friday: '',
      Saturday: '',
      Sunday: '',
    },
  ];
  return (
    <Table
      dataSource={info}
      columns={columns}
      bordered
      pagination={false}
    />
  );
}

export default StudentCourseList;