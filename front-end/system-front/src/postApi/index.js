import axios from 'axios'

const baseUrl = 'http://localhost:8080/'
const courseUrl = 'http://localhost:8081/'

export const Post = (url, params) => {
  return axios.post(baseUrl + url, params)
}

export const coursePost = (url, params) => {
  return axios.post(courseUrl + url, params)
}
