import { Space, Table, Modal, Form, Input, Button, Select } from 'antd';
import React, { useRef } from 'react'
import { useState } from 'react';
import './index.css'
import { useForm } from 'antd/es/form/Form';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';
const { Column } = Table;

/**
* @author
* @function StudentInfoTable
**/

const SimpleTable = (props) => {
  const { info, TITLE_MAP, TITLE_ARR, btnTitle, ARR_TITLE_ARR, changeData, addCourseInfo, getCourseInfo, delCourseInfo } = props
  const [isPop, setIsPop] = useState(false)
  const [selcetData, setSelectData] = useState({})
  const [searchedColumn, setSearchedColumn] = useState('');
  const [searchText, setSearchText] = useState('');
  const searchInput = useRef(null);
  const [istabDisabled, setistabDisabled] = useState(false)
  // 记录当前是什么弹窗
  const popTypeRef = useRef()
  const [form] = useForm()
  const handleSummit = () => {
    setIsPop(false)
    const res = form.getFieldValue()
    addCourseInfo(res)
    getCourseInfo('mongo/schedule/find_all').then(res => {
      changeData(res.data.data.schedule_info)
    })
  }

  const handleSearch = (selectedKeys, confirm, dataIndex) => {
    confirm();
    setSearchText(selectedKeys[0]);
    setSearchedColumn(dataIndex);
  };
  const handleReset = (clearFilters) => {
    clearFilters();
    setSearchText('');
  };

  const selectItem = (item) => {
    setSelectData(info.filter(info => info.id === item.id)[0])
    setIsPop(true)
    setistabDisabled(true)
  }
  const handleCancel = () => {
    setIsPop(false)
    setistabDisabled(false)
  };
  const delInfo = (curInfo) => {
    changeData(info.filter(item => item.id !== curInfo.id))
    delCourseInfo('mongo/schedule/delete_schedule', { id: curInfo.id })
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
      course_id: '',
      course_name: '',
      course_number: '',
      credit: '',
      course_attributes: '',
      exam_type: '',
      tutor: '',
      weekly: '',
      week: '',
      section_id: '',
      section_num: '',
      campus: '',
      building: '',
      class_room: '',
      department: ''
    })
  }

  const getColumnSearchProps = (dataIndex) => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters, close }) => (
      <div
        style={{
          padding: 8,
        }}
        onKeyDown={(e) => e.stopPropagation()}
      >
        <Input
          ref={searchInput}
          placeholder={`Search ${dataIndex}`}
          value={selectedKeys[0]}
          onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
          onPressEnter={() => handleSearch(selectedKeys, confirm, dataIndex)}
          style={{
            marginBottom: 8,
            display: 'block',
          }}
        />
        <Space>
          <Button
            type="primary"
            onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
            icon={<SearchOutlined />}
            size="small"
            style={{
              width: 90,
            }}
          >
            Search
          </Button>
          <Button
            onClick={() => clearFilters && handleReset(clearFilters)}
            size="small"
            style={{
              width: 90,
            }}
          >
            Reset
          </Button>
          <Button
            type="link"
            size="small"
            onClick={() => {
              confirm({
                closeDropdown: false,
              });
              setSearchText(selectedKeys[0]);
              setSearchedColumn(dataIndex);
            }}
          >
            Filter
          </Button>
          <Button
            type="link"
            size="small"
            onClick={() => {
              close();
            }}
          >
            close
          </Button>
        </Space>
      </div>
    ),
    filterIcon: (filtered) => (
      <SearchOutlined
        style={{
          color: filtered ? '#1890ff' : undefined,
        }}
      />
    ),
    onFilter: (value, record) =>
      record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownOpenChange: (visible) => {
      if (visible) {
        setTimeout(() => searchInput.current?.select(), 100);
      }
    },
    render: (text) =>
      searchedColumn === dataIndex ? (
        <Highlighter
          highlightStyle={{
            backgroundColor: '#ffc069',
            padding: 0,
          }}
          searchWords={[searchText]}
          autoEscape
          textToHighlight={text ? text.toString() : ''}
        />
      ) : (
        text
      ),
  });

  const columns = [
    {
      title: '课程名',
      dataIndex: 'course_name',
      key: 'course_name',
      render: (course_name) => {
        return <div className='popDetailInfo'>{course_name}</div>
      },
      ...getColumnSearchProps('course_name')
    }
  ];
  TITLE_ARR.forEach((item, index) => {
    columns.push(
      {
        key: item + index,
        title: TITLE_MAP[item],
        dataIndex: item,
        ...getColumnSearchProps(item)
      }
    )
  })
  ARR_TITLE_ARR.forEach((item, index) => {
    columns.push(
      {
        key: item + index,
        title: TITLE_MAP[item],
        dataIndex: item,
        render:
          (info) => (<div key={item} className='col-arr'>
            {
              info.split(';').map((item, index) => {
                return <div key={item + index}>{item}</div>
              })
            }
          </div>)
      }
    )
  })
  columns.push({
    title: "操作",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a onClick={() => delInfo(record)}>删除</a>
      </Space>
    )
  })

  return (
    <>
      <Button type='primary' onClick={addInfo}>{btnTitle}</Button>
      <Table
        dataSource={info}
        rowKey={(item) => item.unique_id}
        columns={columns}
      >
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
              提交
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </>
  )
}

export default SimpleTable