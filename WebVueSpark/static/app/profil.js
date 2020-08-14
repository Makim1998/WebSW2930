Vue.component('profil',{
	data:function(){
		return{
			stara:"",
			email: "",
            ime: "",
            prezime: "",
            lozinka: "",
            ponovljena: ""
		}
	},
	template: ` 
<div id = "Profil">
    <form>
		<h2 class="text-center">Pregled i promena profila</h2>       
		<div class="form-group">
			<label for="username">Email: </label>
   			<input type="text" id = "username" class="form-control" v-model="email">
		</div>
		<div class = "form-group">
			<label for="ime">Ime: </label>
   			<input type="text" id = "ime" class="form-control" v-model="ime" placeholder="Ime">
   		</div>
		<div class = "form-group">
			<label for="prezime">Prezime: </label>
			<input type="text" id = "prezime" class="form-control" v-model="prezime" placeholder="Prezime">
    	</div>
		<div class="form-group">
			<label for="lozinka">Lozinka: </label>
    		<input type="password" id="lozinka" class="form-control" v-model="lozinka" placeholder="Lozinka">
   		</div>
   		<div class = "form-group">
   			<label for="ponovljena">Ponovite lozinku: </label>
    		<input type="password" id = "ponovljena" class="form-control" v-model="ponovljena" placeholder="Ponovi lozinku">
    	</div>
		<div class="form-group">
    		<button type="button" class="btn btn-primary btn-block" v-on:click="izmeni()">Izmeni profil</button>
		</div>
    </form>
</div>		  		  
`
	, 
	methods : {
		izmeni() {
	    	console.log(this.ime);
	    	if(this.proveraPolja()){
				axios
			    .put('rest/profil',{
			    	"email": this.email,
			    	"ime": this.ime,
		            "prezime": this.prezime,
		            "lozinka":this.lozinka,
		            "stara": this.stara
				})
			    .then((response) => {
			    	alert("Uspesno ste izmenili profil!");
			    	console.log(response.data);
			    	this.email = response.data.email;
			    	console.log(response.data.email);
			    	this.ime = response.data.ime;
			    	this.lozinka = response.data.lozinka;
			    	this.ponovljena = response.data.lozinka;
			    	console.log(this.ime);
			    	this.stara = response.data.email;
	
			    });
	    	}
	    	else{
	    		alert("Popunite ispravno sva polja");
	    	}
			
        },
        proveraPolja(){
        	if(this.lozinka !== this.ponovljena){
        		alert("Niste dobro ponovili lozinku")
        		return false;
        	}
        	if(this.lozinka == ""){
        		alert("Niste uneli lozinku")
        		return false;
        	}
        	if(this.ime == ""){
        		alert("Niste uneli ime")
        		return false;
        	}
        	if(this.prezime == ""){
        		alert("Niste uneli prezime")
        		return false;
        	}
        	if(this.email == ""){
        		alert("Niste uneli email")
        		return false;
        	}
        	return true;
        	
        	
        }
        
	},
	mounted(){
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
	    	console.log(response.data);		    
	    	this.email = response.data.email;
	    	this.ime = response.data.ime;
	    	this.prezime = response.data.prezime;
	    	this.lozinka = response.data.lozinka;
	    	this.stara = response.data.email;
	    	this.ponovljena = response.data.lozinka; 
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});