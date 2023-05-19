import React, { useEffect, useRef, useState } from 'react'
import { Card, Input, Button, Modal, Checkbox, Space, Divider, message } from 'antd';
import './index.css'
import { getInfoApi, changeApi, delApi } from './api'

const SemesterInformation = () => {
  const [info, setInfo] = useState([])
  const [isPopShow, setIsPopShow] = useState(false)
  const [currentSeme, setCurrentSeme] = useState()
  const [messageApi, contextHolder] = message.useMessage();
  const modalRef = useRef()
  useEffect(() => {
    getInfoApi('semester/find_all_semester').then(res => {
      setInfo(res.data.data.semester_info)
    })
    getInfoApi('semester/get_current_semester').then(res => {
      setCurrentSeme(res.data.data.semester_info)
    })
  }, [])

  const changeCurrentSemester = (item) => {
    setCurrentSeme(item)
    changeApi('semester/set_current_semester', { id: item.id, semester: item.semester })
  }

  const changeInfo = () => {
    setIsPopShow(true)
  }
  const handleCancel = () => {
    setIsPopShow(false)
  };
  const summitInfo = async () => {
    const id = Date.now().toString();
    const info = { semester: modalRef.current.input.value }
    await getInfoApi('semester/insert_semester', info)
    await getInfoApi('semester/find_all_semester').then(res => {
      setInfo(res.data.data.semester_info)
    })
    setIsPopShow(false)
  }
  const delSemester = (id) => {
    if (id === currentSeme?.id) {
      messageApi.open({
        type: 'error',
        content: '删除的是当前学期请切换当前学期后尝试'
      });
      return
    }
    const res = info.filter(item => item.id !== id)
    setInfo(res)
    delApi('semester/delete_semester', { id: id })
  }
  return (
    <>
      {contextHolder}
      <Space className='seme-top'>
        <Button onClick={changeInfo} type='primary'>添加学期信息</Button>
        <div>当前学期:{currentSeme?.semester}</div>
      </Space>
      <Card
        title="学期信息"
        bordered={false}
        style={{
          width: '100%',
        }}
      >
        {
          info.map(item => {
            return <React.Fragment key={item.id + item.semester}>
              <div className='semester-p'>
                {item.semester}
                <Space>
                  <Button onClick={() => delSemester(item.id)} danger>删除</Button>
                  <Button onClick={() => changeCurrentSemester(item)} disabled={currentSeme?.id === item.id} type='primary'>设置为当前学期</Button>
                </Space>
              </div>
              <Divider />
            </React.Fragment>
          })
        }
      </Card>
      {
        isPopShow &&
        < Modal title="学期信息" cancelButtonProps open={isPopShow} onCancel={handleCancel} onOk={summitInfo}>
          <Input ref={modalRef} placeholder='例如：2019-2020年秋' />
        </Modal >
      }
    </>
  )
}

export default SemesterInformation