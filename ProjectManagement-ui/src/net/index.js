import axios from "axios";
import {ElMessage} from "element-plus";

const defaultError = () => {
    ElMessage.error("发生了错误，请查看日志！")
}
const defaultFail = (data) => {
    ElMessage.warning(data.msg)
}


function post(url,data,success,fail = defaultFail, error = defaultError) {
    axios.post(url,data,{
        headers:{
            "Content-Type":"application/x-www-form-urlencoded"
        },
        withCredentials:true
        }).then(({data}) => {
            if(data.code === "0000"){
                success(data);
            }else {
                fail(data)
            }
    }).catch(error)
}

function get(url,success,fail,error) {
    axios.get(url,{
        withCredentials:true
    }).then(({data}) => {
        if(data.code === "0000"){
            success(data);
        }else {
            fail(data)
        }
    }).catch(error)
}
export { post , get }