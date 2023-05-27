import { Space, Table, Modal, Form, Input, Button } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import React, { useRef } from 'react'
import { useState } from 'react';
import './index.css'
import { useForm } from 'antd/es/form/Form';
import Highlighter from 'react-highlight-words';
const { Column } = Table;

/**
* @author
* @function StudentInfoTable
**/

const SimpleTable = (props) => {
  const { data, TITLE_MAP, TITLE_ARR, btnTitle, changeData, delData, addData } = props
  const [isPop, setIsPop] = useState(false)
  const [selcetData, setSelectData] = useState({})
  const [istabDisabled, setistabDisabled] = useState(false)
  const [searchedColumn, setSearchedColumn] = useState('');
  const [searchText, setSearchText] = useState('');
  const searchInput = useRef(null);
  // 记录当前是什么弹窗
  const popTypeRef = useRef()
  const [form] = useForm()
  const handleSummit = () => {
    setIsPop(false)
    const infoArr = data.filter(item => selcetData.unique_id !== item.unique_id)
    infoArr.unshift(selcetData)
    if (popTypeRef.current === 'add') {
      addData(selcetData)
      changeData(infoArr, selcetData)
    } else {
      changeData(infoArr, selcetData)
    }
  }

  const inputChange = (e) => {
    selcetData[e.target.name] = e.target.value
    setSelectData({ ...selcetData })
  }

  const handleCancel = () => {
    setIsPop(false)
    setistabDisabled(false)
  };

  const changeInfo = (info) => {
    setSelectData({ ...info })
    setIsPop(true)
    setistabDisabled(false)
    popTypeRef.current = 'change'
  }

  const addInfo = () => {
    setIsPop(true)
    setSelectData({
      unique_id: '',
      name: '',
      id_card_num: undefined,
      phone: '',
      gender: '',
      nationality: '',
      birthday: '',
      exam_from: '',
      gaokao_score: '',
      graduated_school: '',
      department: '',
      major: '',
      campus: '',
      class: '',
      trainning_level: ''
    })
    popTypeRef.current = 'add'
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
      title: '姓名（点击查看详细信息',
      dataIndex: 'name',
      key: 'name',
      width: '30%',
      render: (name) => {
        return <div className='popDetailInfo'>{name}</div>
      },
      ...getColumnSearchProps('name')
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
  columns.push({
    title: "操作",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a onClick={() => changeInfo(record)}>修改信息 {record.lastName}</a>
        <a onClick={() => delData(record)}>删除</a>
      </Space>
    )
  })
  return (
    <>
      {btnTitle.length !== 0 && <Button type='primary' onClick={addInfo}>{btnTitle}</Button>}
      <Table
        dataSource={data}
        rowKey={(item) => item.unique_id}
        columns={columns}
      >
        {
          TITLE_ARR.map((item, index) => {
            return <Column key={item + index} title={TITLE_MAP[item]} dataIndex={item} />
          })
        }
      </Table >
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
            Object.keys(TITLE_MAP).map((item, index) => {
              return selcetData ? (
                <Form.Item key={item + index} labelCol={{ span: 8 }} label={TITLE_MAP[item] + ':'}>
                  <Input name={item} onChange={inputChange} disabled={popTypeRef.current === 'change' && item === 'unique_id'} value={selcetData[item]} />
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