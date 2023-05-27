import {
  AppstoreOutlined
} from '@ant-design/icons';

export const ADMIN_SIDE_BAR = [
  {
    key: 'nav1',
    icon: <AppstoreOutlined />,
    url: '/student-info',
    label: '学生管理'
  }, {
    key: 'nav2',
    icon: <AppstoreOutlined />,
    url: '/teacher-info',
    label: '教师管理'
  }, {
    key: 'nav3',
    icon: <AppstoreOutlined />,
    label: '设置学期信息',
    url: '/semester-info'
  }, {
    key: 'nav4',
    icon: <AppstoreOutlined />,
    label: '录入课程信息',
    url: '/course-info'
  }, {
    key: 'nav6',
    icon: <AppstoreOutlined />,
    label: '设置选课院系',
    url: '/setCourseDepartments'
  }
]

export const THEACHER_SIDE_BAR = [
  {
    key: 'nav1',
    icon: <AppstoreOutlined />,
    url: '/tutor-info',
    label: '个人信息'
  }, {
    key: 'nav2',
    icon: <AppstoreOutlined />,
    label: '成绩课程管理',
    url: '/performance-management'
  }
]

export const STUDENT_SIDE_BAR = [
  {
    key: 'nav1',
    icon: <AppstoreOutlined />,
    url: '/stu-info',
    label: '个人信息'
  }, {
    key: 'nav2',
    icon: <AppstoreOutlined />,
    url: '/update_student_account',
    label: '修改账号信息'
  }, {
    key: 'nav3',
    icon: <AppstoreOutlined />,
    label: '查询成绩',
    url: '/find_score'
  }, {
    key: 'nav4',
    icon: <AppstoreOutlined />,
    label: '选课',
    children: [
      {
        key: 'nav4-1',
        icon: <AppstoreOutlined />,
        label: '正式选课',
        url: '/select_course',
      },
      {
        key: 'nav4-2',
        icon: <AppstoreOutlined />,
        label: '购物车',
        url: '/shopping_cart',
      },
      {
        key: 'nav4-3',
        icon: <AppstoreOutlined />,
        label: '订单查询',
        url: '/order-query',
      },
      {
        key: 'nav4-4',
        icon: <AppstoreOutlined />,
        label: '退课',
        url: '/with-drawal-fromClass',
      }
    ]
  }, {
    key: 'nav5',
    icon: <AppstoreOutlined />,
    label: '课程表',
    url: '/student_course_table'
  }
]

export const ASIDE_MENU_OBJ = new Map([['2', ADMIN_SIDE_BAR], ['1', THEACHER_SIDE_BAR], ['0', STUDENT_SIDE_BAR]])