import { LoadingOutlined } from '@ant-design/icons';
import { Spin } from 'antd';
import React from 'react'

export default function Loading() {
    return (
        <LoadingOutlined
            style={{
                fontSize: 24,
            }}
            spin
        />
    )
}
