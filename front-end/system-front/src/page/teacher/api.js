import { coursePost, Post } from '../../postApi'

export const PostApi = (url, params) => {
  return coursePost(url, params)
}

// 8080
export const PostChangeScore = (url, params) => {
  return Post(url, params)
}