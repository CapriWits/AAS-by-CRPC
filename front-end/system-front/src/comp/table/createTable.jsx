import React from 'react'
import SmpleTable from './index'
import StudentInfoTable from './studentInfoTable'

/**
* @author
* @function 
**/

export const createTable = (props) => {
  const { columns, type } = props
  switch (type) {
    case 'normal':
      return <SmpleTable columns={columns} />
    case 'student-info':
      return <StudentInfoTable columns={columns} />
    default:
      return
  }
}
