import React, { useEffect, useState } from 'react'
import { PostChangeScore, PostApi } from './api'
import todoStore from '../../mobx/store'
import { Table, Button, Modal, Input } from 'antd'
import { toJS } from 'mobx'
import { useLocation } from 'react-router'

export default function WithdrawalFromClass() {
  const [info, setInfo] = useState()
  const [score, setScore] = useState()
  const [id, setId] = useState()
  const [isOpen, setIsOpen] = useState()
  const location = useLocation()
  useEffect(() => {
    const newStore = toJS(todoStore)
    const info = location.state.studentId
    PostApi('class_schedule/find_classmate',
      { course_id: info.course_id, course_num: info.course_number, tutor_id: newStore.teacherInfo.unique_id }
    ).then(res => {
      console.log(res);
      const studentInfo = res.data.data.studentIdNameMap
      const obj = res.data.data.classmates.map(item => {
        return {
          student_name: studentInfo.filter(cur => {
            return item.student_id === cur.studentId
          })[0].studentName,
          student_id: item.student_id,
          id: item.score.id,
          score: item.score.score
        }
      })
      setInfo(obj)
    })
  }, [])


  const changeScore = (e) => {
    setScore(e.target.value)
  }

  const postChangeScore = () => {
    PostChangeScore('score/update_score', {
      id: id,
      score: score
    })
    setIsOpen(false)
    console.log(info);
    info.forEach(item => {
      if (item.id === id) {
        item.score = score
      }
    })
    setInfo([...info])
  }

  const changeIsPop = (isShow, id) => {
    setIsOpen(isShow)
    id && setId(id)
  }
  const columns = [
    {
      title: '学生名',
      dataIndex: 'student_name',
      key: 'student_name',
    },
    {
      title: '学号',
      dataIndex: 'student_id',
      key: 'student_id',
    },
    {
      title: '学号',
      dataIndex: 'score',
      key: 'score',
    },
    {
      title: '操作',
      key: 'action',
      render: (record) => {
        return <Button onClick={() => { changeIsPop(true, record.id) }}>修改分数</Button>
      }
    }
  ];
  return (
    <>
      <Table rowKey={record => record.id} dataSource={info} columns={columns} />;
      <Modal onOk={postChangeScore} title='修改学生成绩' open={isOpen} onCancel={() => changeIsPop(false)}>
        <Input onChange={changeScore} placeholder='请输入成绩'></Input>
      </Modal>
    </>
  )
}
