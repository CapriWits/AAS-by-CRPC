import React from 'react'
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom'
import { lazy, Suspense } from 'react'
import StudentInfo from '../page/admin/studentInfo'
import TeacherInfo from '../page/admin/teacherInfo'
import SemesterInformation from '../page/admin/semesterInformation'
import CourseInfo from '../page/admin/courseInfo'
import Departments from '../page/admin/departments'
import { observer } from "mobx-react-lite";
import StudentCourse from '../page/student/studentCorse'
import PersonalInfo from '../page/student/personalInfo'
import ChangeStudentInfo from '../page/student/changeInfo'
import FindScore from '../page/student/findScore'
import SelectCourse from '../page/student/selectCourse'
import StudentCourseTable from '../page/student/studentCorse'
import ShopCurt from '../page/student/shopCurt'
import OrderQuery from '../page/student/orderQuery'
import WithdrawalFromClass from '../page/student/withdrawalFromClass'
import TutorInfo from '../page/teacher/teacherInfo'
import TheacherStudentInfo from '../page/teacher/student-info'
import PerformanceManagement from '../page/teacher/performanceManagement'
const Login = lazy(() => import('../page/Login/index'))
const Loading = lazy(() => import('../comp/Loading'))
const Home = lazy(() => import('../page/Home/index'))

const Router = observer(() => {

  // const RequireAuth = ({ children }) => {
  //   const token = localStorage.getItem('username');
  //   if (token) {
  //     return <>{children}</>;
  //   } else {
  //     return <Navigate to="/" replace />;
  //   }
  // }

  return (
    <Suspense fallback={<Loading />}>
      <BrowserRouter>
        <Routes>
          <Route index path='/' element={<Login />}></Route>
          <Route path='/home' element={<Home />}>
            <Route path='student-info' element={<StudentInfo />}></Route>
            <Route path='teacher-info' element={<TeacherInfo />}></Route>
            <Route path='semester-info' element={<SemesterInformation />}></Route>
            <Route path='course-info' element={<CourseInfo />}></Route>
            <Route path='setCourseDepartments' element={<Departments />}></Route>
            <Route path='studentCorse' element={<StudentCourse />}></Route>
            <Route path='stu-info' element={<PersonalInfo />}></Route>
            <Route path='update_student_account' element={<ChangeStudentInfo />}></Route>
            <Route path='find_score' element={<FindScore />}></Route>
            <Route path='select_course' element={<SelectCourse />}></Route>
            <Route path='student_course_table' element={<StudentCourseTable />}></Route>
            <Route path='shopping_cart' element={<ShopCurt />}></Route>
            <Route path='order-query' element={<OrderQuery />}></Route>
            <Route path='with-drawal-fromClass' element={<WithdrawalFromClass />}></Route>
            <Route path='tutor-info' element={<TutorInfo />}></Route>
            <Route path='performance-management' element={<PerformanceManagement />}></Route>
            <Route path='teacher-student-info' element={<TheacherStudentInfo />}></Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </Suspense>
  )
})

export default Router