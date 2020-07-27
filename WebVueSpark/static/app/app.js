const Login = { template: '<login></login>' }
const Homepage = { template : '<homepage></homepage>' }

const router = new VueRouter({
	  mode: 'hash',
	  routes: [
		{ path: '/', component: Login},
	    {
	    	path: "/homepage",
	    	name : "homepage",
	    	component : Homepage
	    },
	]
});

var app = new Vue({
	router,
	el: '#app',
	data() {
        return {
            authenticated: false,
        }
    },
    mounted() {
        if(!this.authenticated) {
        	console.log("Not logged in!");
            //this.$router.replace({ name: "login" });
        }
    },
    methods: {
        setAuthenticated(status) {
            this.authenticated = status;
        },
        logout() {
            this.authenticated = false;
        }
    }
});