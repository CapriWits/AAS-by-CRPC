import React, { useState } from 'react'
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined
} from '@ant-design/icons';
import { ADMIN_SIDE_BAR, ASIDE_MENU_OBJ } from '../../constants/index.js'
import { Layout, Menu, theme, Avatar } from 'antd';
import { Outlet, useNavigate } from 'react-router'
import './index.css'
import todoStore from '../../mobx/store.js';
const { Header, Sider, Content } = Layout;

export default function Index() {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate()
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const goTo = (e) => {
    e.domEvent.preventDefault()
    navigate(`/home${e.item.props.url}`)
  }
  const role = window.localStorage.getItem('role')
  const asideMenuObj = ASIDE_MENU_OBJ.get(role)
  const backForward = () => {
    window.localStorage.removeItem('todoStorage')
    navigate('/')
  }
  return (
    <Layout className='layout'>
      <Sider trigger={null} collapsible collapsed={collapsed}>
        <div className="logo">
          教务管理系统
        </div>
        <div className="sub">
          AAS-By-CRPC
        </div>
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={['1']}
          items={asideMenuObj}
          onClick={(e) => { goTo(e) }}
        />
      </Sider>
      <Layout className="site-layout">
        <Header
          style={{
            padding: 0,
            background: colorBgContainer,
          }}
        >
          {React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
            className: 'trigger',
            onClick: () => setCollapsed(!collapsed),
          })}
          <div onClick={backForward} className='icon'>
            <Avatar icon={<UserOutlined />}></Avatar>
            <div className='mask'>退出</div>
          </div>
        </Header>
        <Content
          style={{
            margin: '24px 16px',
            padding: 24,
            minHeight: 280,
            background: colorBgContainer,
          }}
        >
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  )
}
