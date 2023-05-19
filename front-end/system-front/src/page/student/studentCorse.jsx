import React, { useEffect, useState } from 'react'
import StudentCourseList from '../../comp/table/studentCourseList'
import { PostApi } from './api'
import todoStore from '../../mobx/store'
import { toJS } from 'mobx'

export default function StudentCorse() {
  const [info, setInfo] = useState()
  useEffect(() => {
    const store = toJS(todoStore)
    PostApi('class_schedule/find_by_studentId_semesterId',
      {
        student_id: store.studentInfo.unique_id,
        semester_id: store.semester_id
      }
    ).then(res => {
      console.log(res.data.data.schedule_tables);
      setInfo(res.data.data.schedule_tables)
    })
  }, [])
  return (
    <StudentCourseList info={info} />
  )
}
