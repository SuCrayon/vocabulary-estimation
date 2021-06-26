/*
 * @Author: Crayon
 * @Date: 2021-06-26 11:10:44
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 11:12:56
 */
const isMobile_regex = /Mobile|Android|webOS|iPhone|iPod|BlackBerry/i

export function isPC() {
    return !isMobile_regex.test(navigator.userAgent)
}