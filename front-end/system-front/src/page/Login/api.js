import { Post } from '../../postApi/index'

export const postLogin = (url, params) => {
  return Post(url, params)
}