Vue.component("login", {
	data: function () {
		    return {
		    	 input: {
	                    username: "",
	                    password: ""
	                },
	             podaci:null,
		    }
	},
	template: `
<div id = "login">
    <form>
		<h2 class="text-center">Prijava</h2>       
		<div class="form-group">
   			<input type="text" class="form-control" v-model="input.username" placeholder="email">
		</div>
		<div class="form-group">
    		<input type="password" class="form-control" v-model="input.password" placeholder="Password">
		</div>
		<div class="form-group">
    		<button type="button" class="btn btn-primary btn-block" v-on:click="login()">Log in</button>
		</div>
    </form>
</div>		  
` 
		,
	mounted() {
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
			if(response.data.uloga == "SUPER_ADMIN"){
				console.log("Ulogovao se super admin");
				//this.$router.replace({ name: "pacijentHome" });
			}
			else if(response.data.uloga== "ADMIN"){
				console.log("Ulogovao se admin");
				//this.$router.replace({ name: "administratorKlinike" });
			}
			else{
				console.log("Ulogovao se korisnik");
				//this.$router.replace({ name: "MSHome" }) 
			}	
		});
	    },
	methods : {
		login() {
            if(this.input.username != "" && this.input.password != "") {
                /*if(this.input.username == "pacijent" && this.input.password == "pacijent") {
                    this.$emit("authenticated", true);
                    this.$router.replace({ name: "homepage" });
                } else {
                    console.log("The username and / or password is incorrect");
                }*/
            	axios
    			.post('rest/login/'+ this.input.username+ "/"+this.input.password)
    			.then((response) => {
					console.log("uspesno logovanje");
    				console.log(response);
    				console.log(response.data);
    				console.log(response.data.uloga);
    				if(response.data == null){
    					alert("Pogresni kor. ime i lozinka");
    				}   					
    				else if(response.data.uloga == "SUPER_ADMIN"){
    					console.log("Ulogovao se super admin");
    					this.$router.replace({ name: "homepage" });
    				}
    				else if(response.data.uloga== "ADMIN"){
    					console.log("Ulogovao se admin");
    					//this.$router.replace({ name: "administratorKlinike" });
    				}
    				else{
    					console.log("Ulogovao se korisnik");
    					//this.$router.replace({ name: "MSHome" }) 
    				}
    			 })
    			
    			.catch(function(error){
    				if(error.response){
    					alert("Pogresni kor. ime i lozinka");
    				};
    			});
            } else {
                console.log("A username and password must be present");
            }
        },
	},

});