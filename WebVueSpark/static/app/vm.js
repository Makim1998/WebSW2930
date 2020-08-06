Vue.component('vm',{
	data: function(){
		return {
			vm:[],
			validacija: false,
			ime:"",
			broj: "",
			ram: "",
			gpu: "",
			stara: "",
			organizacija: "",
			superAdmin: false,
			admin: false,
			korisnik: false
		}
		
	},
	template: ` 
	<div id = "vmSAdmin" style="margin-left: 260px;">
		<h2 class="text-center">Virtuelne masine</h2>
<table class="table table-striped" id = "vmasine">
  <thead>
    <tr>
      <th scope="col">Ime</th>
      <th scope="col">Broj jezgara</th>
      <th scope="col">RAM gB</th>
      <th scope="col">GPU jezgra</th>
      <th scope="col">Organizacija</th>
    </tr>
  </thead>
  <tbody>
		<tr v-for="v in vm">
			<td >{{v.ime}}</td>
			<td >{{v.brojJezgara}}</td>
			<td >{{v.RAM}}</td>
			<td >{{v.GPU}}</td>
			<td >{{v.organizacija}}</td>
			
			<td>
			<button v-if = "superAdmin == true" type="button" v-on:click = "obrisi(v.ime)" class="btn btn-primary">
				Obrisi
			</button>
			</td>
		</tr>
  </tbody>
</table>

	</div>
`
	, 
	methods : {
        obrisi(ime){
        	console.log(ime);
        	axios
		    .get('rest/obrisiVM/' + ime )
		    .then((response) => {
		    	console.log(response.data);
		    	axios
			    .get('rest/getSveVM')
			    .then((response) => {
			    	console.log(response.data.virtualneMasine);
			    	this.vm = response.data.virtualneMasine;
			    });
		    })
		    .catch(function(error){
				if(error.response){
					alert(error.response.data);
				};
        	});
        }
        
        
	},
	mounted() {
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
	    	console.log(response.data);
	    	if(response.data.uloga == "SUPER_ADMIN"){
	    		this.superAdmin = true;
	    	}
	    	if(response.data.uloga == "ADMIN"){
	    		this.admin = true;
	    	}
	    	if(response.data.uloga == "KORISNIK"){
	    		this.korisnik = true;
	    	}
	    	axios
		    .get('rest/getSveVM')
		    .then((response) => {
		    	console.log(response.data.virtualneMasine);
		    	this.vm = response.data.virtualneMasine;
		    });
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});