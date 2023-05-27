import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, Checkbox, Form, Input, message } from 'antd'
import { postLogin } from './api'
import { autorun } from 'mobx'
import todoStore from '../../mobx/store'
import './index.css'


const Index = function Index() {
  const [messageApi, contextHolder] = message.useMessage();
  const navigate = useNavigate()
  const role = undefined
  let isNavfate = false
  const onFinish = async (values) => {
    await postLogin('/login', { id: values.id, password: values.password }).then(res => {
      if (res.data.code === 200) {
        if (res.data.data.role === 0) {
          todoStore.changeStudentInfo(res.data.data.student_info)
          todoStore.changeCanSelectClass(res.data.data.can_select_class)
        } else if (res.data.data.role === 1) {
          todoStore.changeTeacherInfo(res.data.data.tutor_info)
        }
        window.localStorage.setItem('role', res.data.data.role)
        window.localStorage.setItem('id', values.id)
        window.localStorage.setItem('department', values.id)
        isNavfate = true
      } else {
        messageApi.open({
          type: 'error',
          content: `${res.data.msg}`,
        });
      }
    })

    await postLogin('semester/get_current_semester').then(res => {
      console.log(res);
      todoStore.changeSemesterId(res.data.data.semester_info.id)
    })
    if (isNavfate) {
      navigate('/home')
    }
  };
  return (
    <div className='bg-container'>
      <div className='form-container'>
        {contextHolder}
        <Form
          className='ant-form-login'
          name="basic"
          labelCol={{
            span: 4,
          }}
          wrapperCol={{
            span: 16,
          }}
          style={{
            maxWidth: 600,
          }}
          initialValues={{
            remember: true,
          }}
          onFinish={onFinish}
          autoComplete="off"
        >
          <Form.Item
            label="教务管理系统"
            labelCol={{ span: 13 }}
          >
          </Form.Item>
          <Form.Item
            label="用户："
            name="id"
            rules={[
              {
                required: true,
                message: '请输入账号!',
              },
            ]}
          >
            <Input />
          </Form.Item>

          <Form.Item
            label="密码："
            name="password"
            rules={[
              {
                required: true,
                message: '请输入密码!',
              },
            ]}
          >
            <Input.Password />
          </Form.Item>

          <Form.Item
            name="remember"
            valuePropName="checked"
            wrapperCol={{
              offset: 10,
              span: 5,
            }}
          >
          </Form.Item>

          <Form.Item
            wrapperCol={{
              offset: 10,
              span: 5,
            }}
          >
            <Button type="primary" htmlType="submit">
              登录
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  )
}

export default Index