import { Space, Table, Modal, Form, Input, Button, Select } from 'antd';
import React, { useRef } from 'react'
import { useState } from 'react';
import { useForm } from 'antd/es/form/Form';
import { getDateFunc } from '../../util/index'
import './index.css'
const { Column } = Table;

/**
* @author
* @function StudentInfoTable
**/

const SimpleTable = (props) => {
  const { data, TITLE_MAP, TITLE_ARR } = props
  const [isPop, setIsPop] = useState(false)
  const [selcetData, setSelectData] = useState({})
  const [istabDisabled, setistabDisabled] = useState(false)
  const [info, setInfo] = useState(data)
  // 记录当前是什么弹窗
  const popTypeRef = useRef()
  const [form] = useForm()
  const handleSummit = () => {
    setIsPop(false)
    const date = getDateFunc()
    const res = form.getFieldValue()
    res.update_at = date
    const infoArr = info.filter(item => selcetData.unique_id !== item.unique_id)
    infoArr.unshift(res)
    setInfo([...infoArr])
  }

  const selectItem = (item) => {
    setSelectData(data.filter(info => info.unique_id === item.unique_id)[0])
    setIsPop(true)
    setistabDisabled(true)
  }
  const handleCancel = () => {
    setIsPop(false)
    setistabDisabled(false)
  };
  const delInfo = (info) => {
    setInfo(pre => { return pre.filter(item => item.unique_id !== info.unique_id) })
  }
  const changeInfo = (info) => {
    setSelectData(info)
    setIsPop(true)
    setistabDisabled(false)
    popTypeRef.current = 'change'
  }
  const addInfo = () => {
    setIsPop(true)
    setSelectData({
    })
  }
  const search = (val) => {

  }
  return (
    <>
      <Table
        dataSource={info}
        rowKey={(item) => item.unique_id}
      >
        <Column title='姓名（点击查看详细信息）' dataIndex='name' key='name' onCell={(item) => ({ onClick: () => selectItem(item) })} render={(name) => {
          return <div className='popDetailInfo'>{name}</div>
        }} />
        {
          TITLE_ARR.map((item, index) => {
            return <Column key={item + index} title={TITLE_MAP[item]} dataIndex={item} />
          })
        }
        <Column
          title="Action"
          key="action"
          render={(_, record) => (
            <Space size="middle">
              <a onClick={() => changeInfo(record)}>修改信息 {record.lastName}</a>
              <a onClick={() => delInfo(record)}>删除</a>
            </Space>
          )}
        />
      </Table>
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
          initialValues={selcetData}
        >
          {
            Object.keys(TITLE_MAP).map((item, index) => {
              const value = selcetData?.hasOwnProperty(item) ? selcetData[item] : ''
              const title = selcetData?.hasOwnProperty(item) ? TITLE_MAP[item] : ''
              return selcetData ? (
                <Form.Item key={value + index} labelCol={{ span: 8 }} name={item} label={title + ':'}>
                  <Input name={item} disabled={istabDisabled} value={value} />
                </Form.Item>) : ''
            })
          }
          <Form.Item wrapperCol={{ offset: 12, span: 18 }}>
            <Button onClick={handleSummit} type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  )
}

export default SimpleTable