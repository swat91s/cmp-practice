import axios from 'axios'
/*const SERVER_URL = 'http://localhost:8081';
const instance = axios.create({
    baseURL : SERVER_URL,
    timeout: 1000
});*/
export default{
    saveStudentData(studentFormData){
        return axios.post(`http://localhost:8081/api/students`,studentFormData, {
            'headers': {
                'Content-Type': 'application/json',
                'Cache-Control': 'no-cache'
            }
        })
    },
    getStudentData(studentID){
        return axios.get(`http://localhost:8081/api/students/` + studentID, {
            'headers': {
                'Content-Type': 'application/json',
                'Cache-Control': 'no-cache'
            }
        })
    }

}
