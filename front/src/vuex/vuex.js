import { createStore } from 'vuex'
import createPersistedState from 'vuex-persistedstate'


const store = createStore({
    state() {
        return {
            touch: false,
            touch2: false,
            navstate: 0,
            cgvnav: true,
            ciback: 3,
            joincli: 1,
            genre: 0,
            board: 0,
            mycgv: 1,
            logged: false,
            role: false,
            like: false,
            likeicon: require('../assets/board/likebtncen.png'),
            search: '',
            videoi: -1,
            drop: 0,
        }
    },

    getters: {
        getLogged(state) {
            return state.logged;
        },
        getRole(state) {
            return state.role;
        },
        getLike(state) {
            return state.like;
        },
        getLikeicon(state) {
            return state.likeicon;
        },
        getSearch(state) {
            return state.search;
        },
        getvideoi(state) {
            return state.videoi;
        },
        getcgvnav(state) {
            return state.cgvnav;
        },
        getdrop(state) {
            return state.drop;
        },
        gettouch2(state) {
            return state.touch2;
        }
    },



    mutations: {


        searchm(state, e) {
            state.search = e;
        },

        touchm(state, e) {
            state.touch = e;
        },
        touch2m(state, e) {
            state.touch2 = e;
            console.log(state.touch2);
        },
        navm(state, e) {
            state.navstate = e;
        },
        cgvnavm(state, e) {
            state.cgvnav = e;
        },
        cibackm(state, e) {
            state.ciback = e;
        },
        joincli(state, e) {
            state.joincli = e;
        },
        genre(state, e) {
            state.genre = e;
        },
        boardm(state, e) {
            state.board = e;
        },
        mycgvm(state, e) {
            state.mycgv = e;
        },
        setLogged(state, value) {
            state.logged = value;
        },
        setRole(state, value) {
            state.role = value;
        },
        setLike(state, value) {
            state.like = value;
        },
        setLikeicon(state, value) {
            state.likeicon = value;
        },
        videoim(state, value) {
            state.videoi = value
        },
        setdrop(state, value) {
            state.drop = value
        }
    },
    actions: {

    },
    plugins: [
        createPersistedState()
    ]
})

export default store 