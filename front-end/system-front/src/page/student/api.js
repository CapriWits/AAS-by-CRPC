import { coursePost } from '../../postApi'

export const PostApi = (url, params) => {
  return coursePost(url, params)
}