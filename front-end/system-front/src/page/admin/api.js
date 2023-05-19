import { Post, coursePost } from '../../postApi/index'

export const getInfoApi = (url, params) => {
  return Post(url, params)
}

export const changeApi = (url, params) => {
  return Post(url, params)
}

export const addApi = (url, params) => {
  return Post(url, params)
}

export const delApi = (url, params) => {
  return Post(url, params)
}

export const getCourseInfo = (url, params) => {
  return coursePost(url, params)
}
export const addCourseInfo = (url, params) => {
  return coursePost(url, params)
}

export const delCourseInfo = (url, params) => {
  return coursePost(url, params)
}