import { Space, Table, Modal, Form, Input, Button } from 'antd';
import React, { useEffect, useRef } from 'react'
import { useState } from 'react';
import './index.css'
import { useForm } from 'antd/es/form/Form';
import Highlighter from 'react-highlight-words';
import { SearchOutlined } from '@ant-design/icons';

/**
* @author
* @function StudentInfoTable
**/

const SimpleTable = (props) => {
  const { info, TITLE_MAP, TITLE_ARR, btnTitle, ARR_TITLE_ARR, publishCourse, inputs, changeInputs, setDepartments } = props
  const [isPop, setIsPop] = useState(false)
  const [isPublishPop, setIsPublishPop] = useState(false)
  const [selcetData, setSelectData] = useState({})
  const [searchedColumn, setSearchedColumn] = useState('');
  const [searchText, setSearchText] = useState('');
  const searchInput = useRef(null);
  const [istabDisabled, setistabDisabled] = useState(false)
  const [idCount, setIdCount] = useState(2);
  const [courseInput, setCourseInput] = useState({ course_id: '', course_num: '', sku: '' })
  // 记录当前是什么弹窗
  const popTypeRef = useRef()
  const [form] = useForm()

  function handleInputChange(event, inputId) {
    const newInputs = inputs.map((input) => {
      if (input.id === inputId) {
        return { id: inputId, value: event.target.value };
      }
      return input;
    });
    changeInputs(newInputs);
  }

  function handleCourseInput(e) {
    setCourseInput(pre => {
      pre.sku = e.target.value
      return pre
    })
  }

  function handleAddInput() {
    setIdCount(idCount + 1);
    changeInputs([...inputs, { id: idCount, value: '' }]);
  }

  function handleRemoveInput(inputId) {
    const newInputs = inputs.filter((input) => input.id !== inputId);
    changeInputs(newInputs);
  }
  const handleSummit = () => {
    const res = inputs.map(item => {
      return item.value
    })
    setIsPop(false)
    setIsPublishPop(false)
    setDepartments(res)
  }

  const handleCancel = () => {
    setIsPop(false)
    setIsPublishPop(false)
    setistabDisabled(false)
  };

  const addInfo = () => {
    setIsPop(true)
    setSelectData()
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
    render: (_, item) => (
      <Space size="middle">
        <a onClick={() => changeCoursePop(item)}>发布课程</a>
      </Space>
    )
  })

  const publishCourseInfo = () => {
    publishCourse(courseInput)
    setIsPublishPop(false)
    // setCourseInput({ course_id: '', course_num: '', sku: '' })
  }

  const changeCoursePop = (item) => {
    console.log(item);
    setIsPublishPop(true)
    setCourseInput(pre => {
      pre.course_id = item.course_id
      pre.course_num = item.course_number
      return pre
    })
  }


  return (
    <>
      <Button type='primary' onClick={addInfo}>{btnTitle}</Button>
      <Table
        dataSource={info}
        rowKey={(item) => item.unique_id}
        columns={columns}
      >
      </Table>
      <Modal title="可选课院系" cancelButtonProps open={isPop} onCancel={handleCancel} footer={null}>
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
          < Button onClick={handleAddInput}>+</Button>
          {
            inputs.map((input) => {
              return (<Form.Item key={input.id} labelCol={{ span: 8 }} label={'选课学院:'}>
                <Space>
                  <Input
                    type="text"
                    value={input.value}
                    onChange={(event) => handleInputChange(event, input.id)}
                  />
                  <Button name={input} onClick={() => handleRemoveInput(input.id)}>-</Button>
                </Space>
              </Form.Item>
              )
            }
            )
          }
          <Form.Item wrapperCol={{ offset: 12, span: 18 }}>
            <Button onClick={handleSummit} type="primary" htmlType="submit">
              提交
            </Button>
          </Form.Item>
        </Form>
      </Modal >
      <Modal title="设置发布课程" cancelButtonProps open={isPublishPop} onCancel={handleCancel} footer={null}>
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
          <Form.Item labelCol={{ span: 8 }} label={'课余量:'}>
            <Space>
              <Input
                type="text"
                onChange={(e) => handleCourseInput(e)}
              />
            </Space>
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 12, span: 18 }}>
            <Button onClick={publishCourseInfo} type="primary" htmlType="submit">
              提交
            </Button>
          </Form.Item>
        </Form>
      </Modal >
    </>
  )
}

export default SimpleTable