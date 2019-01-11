<template>
<div class = "student">
    <div class="student_content">
        <div class="main-label"><h1>STUDENT DETAILS FORM:</h1></div>
        <div v-if="successFlag">Thanks for submitting your details.</div>
        <form id="app">
            <div class="student_content_header">
                <div class="form-label">Student ID:</div>
                <input v-model="formData.id" type="text" id="id" placeholder="Student ID">
                <div class="form-label">Name:</div>
                <input v-model="formData.name" type="text" id="name" placeholder="Student Name">
                <div class="form-label">Passport Number:</div>
                <input v-model="formData.passportNumber" type="text" id="passportNumber" placeholder="Passport Number"> 
                <br />
                <input type="button" value="Submit" @click="saveStudentData">
                <input type="button" value="Cancel" @click="cancelClick">
                <input type="button" value="Get Student By Id" @click="getStudentData">
            </div>
        </form>
    </div>
</div>
</template>

<script>
import api from '../api'
export default{
    name: 'Student',
    data() {
        return {
            formData : {
                id: '',
                name: '',
                passportNumber: ''
            },
            successFlag: false
        }
    },
    mounted(){
    },
    methods:{
        getStudentData : function(){
            let studID = this.formData.id
            api.getStudentData(studID).then((response) => {
                console.log(response.data.result.student)
                this.formData.name = response.data.result.student.name
                this.formData.passportNumber = response.data.result.student.passportNumber
            })
        },  
        saveStudentData : function(){
            var studentObj = {
                "id" : this.formData.id,
                "name" : this.formData.name,
                "passportNumber" : this.formData.passportNumber
            }
            api.saveStudentData(studentObj).then((response) => {
                this.successFlag = true
            })
        },
        cancelClick : function(){
            this.formData = {
                id: '',
                name: '',
                passportNumber: ''
            }
        }
    }
}
</script>

<style>
</style>


