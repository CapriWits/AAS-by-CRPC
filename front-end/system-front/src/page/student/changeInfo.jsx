import React, { useEffect, useState } from 'react'
import todoStore from '../../mobx/store';
import { Button, Descriptions, Modal, Form, Input } from 'antd';
import { useForm } from 'antd/es/form/Form';
import { Post } from '../../postApi/index'

export default function ChangeInfo() {
  const [studentInfo, setStudentInfo] = useState()
  const [info, setInfo] = useState()
  const [isPop, setIsPop] = useState(false)
  const studentInfoArr = [
    {
      id: 'name',
      title: '姓名',
    },
    {
      id: 'id_card_num',
      title: '身份证号'
    },
    {
      id: 'phone',
      title: '电话'
    },
    {
      id: 'gender',
      title: '性别'
    },
    {
      id: 'nationality',
      title: '民族'
    },
    {
      id: 'birthday',
      title: '生日'
    },
    {
      id: 'graduated_school',
      title: '毕业院校'
    },
    {
      id: 'department',
      title: '院系'
    },
    {
      id: 'major',
      title: '专业'
    },
    {
      id: 'campus',
      title: '校区'
    },
    {
      id: 'class',
      title: '班级'
    },
    {
      id: 'trainning_level',
      title: '培训方案'
    }
  ]

  const inputChange = (e) => {
    console.log(e.target.name);
    studentInfo[e.target.name] = e.target.value
    setStudentInfo({ ...studentInfo })
  }

  const handleCancel = () => {
    setIsPop(false)
  };

  const [form] = useForm()

  const changeIsPop = () => {
    setIsPop(true)
  }

  const handleSummit = () => {
    console.log(studentInfo);
    const addObj = {
      'unique_id': todoStore.studentInfo.unique_id,
      'gaokao_score': todoStore.studentInfo.gaokao_score
    }
    setIsPop(false)
    Post('student/update_student_info', {
      ...studentInfo, ...addObj
    })
  }

  useEffect(() => {
    Post('student/get_student_info_with_filter', { filter: { id: todoStore.studentInfo.unique_id, name: null, department: null } }).then(res => {
      setStudentInfo(res.data.data.student_info[0])
      setInfo(res.data.data.student_info[0])
    })
  }, [])

  return (
    <>
      <Button onClick={changeIsPop} type='primary'>修改学生信息</Button>
      <Descriptions bordered>
        <Descriptions.Item label="姓名">{info?.name}</Descriptions.Item>
        <Descriptions.Item label="学号">{info?.unique_id}</Descriptions.Item>
        <Descriptions.Item label="学分券">{info?.coupon}</Descriptions.Item>
        <Descriptions.Item label="身份证">{info?.id_card_num}</Descriptions.Item>
        <Descriptions.Item label="电话" span={2}>{info?.phone}</Descriptions.Item>
        <Descriptions.Item label="性别">{info?.gender}</Descriptions.Item>
        <Descriptions.Item label="民族">{info?.nationality}</Descriptions.Item>
        <Descriptions.Item label="生日">{info?.natiobirthdaynality}</Descriptions.Item>
        <Descriptions.Item label="高考生源">{info?.exam_from}</Descriptions.Item>
        <Descriptions.Item label="高考分数">{info?.gaokao_score}</Descriptions.Item>
        <Descriptions.Item label="毕业学校">{info?.graduated_school}</Descriptions.Item>
        <Descriptions.Item label="学院">{info?.department}</Descriptions.Item>
        <Descriptions.Item label="校区">{info?.campus}</Descriptions.Item>
        <Descriptions.Item label="班级">{info?.class}</Descriptions.Item>
        <Descriptions.Item label="培养层次">{info?.trainning_level}</Descriptions.Item>
      </Descriptions>
      <Modal title="学生详细信息" cancelButtonProps open={isPop} onCancel={handleCancel} footer={null}>
        <Form
          wrapperCol={{
            span: 14,
          }}
          layout="horizontal"
          style={{
            maxWidth: 600,
          }}
          form={form}
        >
          {
            studentInfoArr.map(item => {
              return (
                <Form.Item key={item.id} labelCol={{ span: 8 }} label={item.title}>
                  <Input name={item.id} onChange={inputChange} value={studentInfo && studentInfo[item.id]} />
                </Form.Item>)
            })
          }
          <Form.Item wrapperCol={{ offset: 12, span: 18 }}>
            <Button onClick={handleSummit} type="primary" htmlType="submit">
              提交
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  )
}
