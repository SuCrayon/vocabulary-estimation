/*
 * @Author: Crayon
 * @Date: 2021-07-02 22:04:12
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-07-02 22:26:59
 */

export default {
    // 创建连接
    createConn() {
        if (typeof (WebSocket) === "undefined") {
            return null;
        }
        return new WebSocket("ws://localhost:28000/ve/wordBattle")
    }
}