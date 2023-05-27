import { makeAutoObservable } from 'mobx';
import { makePersistable, isHydrated } from 'mobx-persist-store';

class Todo {
  constructor() {
    makeAutoObservable(this, {}, { autoBind: true })
    makePersistable(this, {  // 在构造函数内使用 makePersistable
      name: 'todoStorage', // 保存的name，用于在storage中的名称标识，只要不和storage中其他名称重复就可以
      properties: ["user", "role", "studentInfo", "teacherInfo", "can_select_class", "semester_id", "shopCurt"], // 要保存的字段，这些字段会被保存在name对应的storage中，注意：不写在这里面的字段将不会被保存，刷新页面也将丢失：get字段例外。get数据会在数据返回后再自动计算
      storage: window.localStorage, // 保存的位置：看自己的业务情况选择，可以是localStorage，sessionstorage
      // 。。还有一些其他配置参数，例如数据过期时间等等，可以康康文档，像storage这种字段可以配置在全局配置里，详见文档
    })
  }
  user = ''
  role = ''
  can_select_class = ''
  semester_id = ''
  studentInfo = {}
  teacherInfo = {}
  shopCurt = []
  changeUser(user) {
    this.user = user
  }
  changeRole(role) {
    this.role = role
  }
  changeStudentInfo(studentInfo) {
    this.studentInfo = studentInfo
  }
  changeTeacherInfo(teacherInfo) {
    this.teacherInfo = teacherInfo
  }
  changeCanSelectClass(can_select_class) {
    this.can_select_class = can_select_class
  }
  changeSemesterId(semester_id) {
    this.semester_id = semester_id
  }
  changeShopCurt = (shopCurt) => {
    this.shopCurt = shopCurt
  }
  get isHydrated() {
    return isHydrated(this);
  }
}
const todoStore = new Todo();
export default todoStore;