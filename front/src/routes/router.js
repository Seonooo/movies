import { createWebHistory, createRouter } from "vue-router";

import HomeView from '../components/HomeView'
import JoinView from '../components/JoinView'
import LoginView from '../components/LoginView'
import reservation from '../components/Reservation'
import cinema1 from '../components/cinemaview/Cinema1View'
import cinema2 from '../components/cinemaview/Cinema2View'
import BoardView from '../components/BoardView'
import BoardWrite from '../components/BoardWrite/BoardWrite'
import MoviePage from '../components/MoviePage'
import boardcontent from "../components/board/boardcontent";
import boardupdate from "../components/board/boardupdate";
import ttt from "../components/board/ttt";
import mycgv from "../components/mycgv/mycgv";
import eventview from '../components/event/eventview'
import gpa from '../components/movie/Gpa'
import movie from '../components/movie/Movie'
import movieone from '../components/movie/MovieOne'
import posters from '../components/movie/Posters'
import story from '../components/movie/Story'
import sche1 from "../components/sche/sche1.vue";
import sche2 from "../components/sche/sche2.vue";
import sche3 from "../components/sche/sche3.vue";
import Payment from "../components/sche/Payment.vue";
import kakaoPaySuccess from "../components/sche/kakaoPaySuccess.vue";
import couple from "../../src/components/cinemaview/couple";
import moviechat from "../components/movie/moviechat"
import homemove from "../components/homemove";



const routes = [
    {
        path: '/',
        component: HomeView,
        name: "HomeView"
    },
    {
        path: '/join',
        component: JoinView,
        name: "JoinView"
    },
    {
        path: '/Login',
        component: LoginView,
        name: "LoginView"
    },
    {
        path: '/reservation',
        component: reservation,
        name: "reservation"
    },
    {
        path: '/cinema1',
        component: cinema1,
        name: "cinema1"
    },
    {
        path: '/cinema2',
        component: cinema2,
        name: "cinema2"
    },
    {
        path: '/BoardView',
        component: BoardView,
        name: "BoardView"
    },
    {
        path: '/BoardWrite',
        component: BoardWrite,
        name: "BoardWrite"
    },
    {
        path: '/MoviePage',
        component: MoviePage,
        name: "MoviePage"
    },
    {
        path: '/boardcontent',
        component: boardcontent,
        name: "boardcontent"
    },
    {
        path: '/boardupdate',
        component: boardupdate,
        name: "boardupdate"
    },
    {
        path: '/ttt',
        component: ttt,
        name: "ttt"
    },
    {
        path: '/mycgv',
        component: mycgv,
        name: "mycgv"
    },
    {
        path: '/eventview',
        component: eventview,
        name: "eventview"
    },
    {
        path: '/gpa',
        component: gpa,
        name: "gpa"
    },
    {
        path: '/movie',
        component: movie,
        name: "movie"
    },
    {
        path: '/movieone',
        component: movieone,
        name: "movieone"
    },
    {
        path: '/posters',
        component: posters,
        name: "posters"
    },
    {
        path: '/story',
        component: story,
        name: "story"
    },
    {
        path: '/sche1',
        component: sche1,
        name: "sche1"
    },
    {
        path: '/sche2',
        component: sche2,
        name: "sche2"
    },
    {
        path: '/sche3',
        component: sche3,
        name: "sche3"
    },
    {
        path: '/Payment',
        component: Payment,
        name: "Payment"
    },
    {
        path: '/kakaoPaySuccess',
        component: kakaoPaySuccess,
        name: "kakaoPaySuccess"
    },
    {
        path: '/couple',
        component: couple,
        name: "couple"
    },
    {
        path: '/moviechat',
        component: moviechat,
        name: "moviechat"
    },
    {
        path: '/ROOT/vue',
        component: homemove,
        name: "homemove"
    },
];


const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior() {
        return { top: 0 }
    },
});

export default router; 