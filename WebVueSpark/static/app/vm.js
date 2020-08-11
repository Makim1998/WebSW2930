Vue.component('vm',{
	data: function(){
		return {
			vm:[],
			svidiskovi:[],
			svekategorije:[],
			organizacije:[],
			validacija: false,
			ime:"",
			broj: "",
			ram: "",
			gpu: "",
			stara: "",
			organizacija: "",
			kategorija: "",
			diskovi:[],
			superAdmin: false,
			admin: false,
			korisnik: false,
			filter: {
	    		ime: "",
	    		broj: "",
                ram: ""
	    	}
		}
		
	},
	template: ` 
	<div id = "vmSAdmin" style="margin-left: 260px;">
		<h2 class="text-center">Virtuelne masine</h2>
		<div id = "filterVm">
<form>
  <div class="form-row">
    <div class="col-sm-2 my-1">
      <label for = "rami" class="col-form-label">Ram: </label>
		<select id = "rami" v-model = "filter.ram" class= "form-control">
			<option value = "">Sve</option>
			<option value = "1">1-4</option>
			<option value = "4">4-8</option>
			<option value = "8">8-16</option>
			<option value = "16">16-32</option>
			<option value = "32">32-64</option>
		</select>
    </div>
    <div class="col-sm-4 my-1">
      <label for = "brojj" class="col-sm-4 col-form-label">Broj jezgara: </label>
		<select id = "brojj" v-model = "filter.broj" class= "form-control">
			<option value = "">Sve</option>
			<option value = "1">1-2</option>
			<option value = "2">2-4</option>
			<option value = "4">4-8</option>
		</select>
    </div>
    <div class="col-sm-3 my-1">
      <label for = "imee" class="col-sm-2 col-form-label">Ime: </label>
		<input id = "imee" type = "text" v-model = "filter.ime" class= "form-control">
    </div>
    <div class="col-sm-3 my-1">
		<label for = "b" class="col-sm-2 col-form-label"> </label>
      <button id = "spusti" type="button" class="btn btn-primary" v-on:click="ponistiFilter()">Prikazi sve</button>
    </div>
  </div>
</form>
</div><br>
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
		<tr v-for="v in filtriranevm">
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

<h5 class="text-center" id = "rezultatiPretrage" v-if="rezultati">Nema rezultata pretrage</h5>

<!-- Button trigger modal -->
<button id = "Dodaj" v-on:click = "isprazniPolja()" type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Dodaj virtuelnu masinu
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Dodavanje virtuelne masine</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "dodajVM">  
       		<div class="form-group">
          		Ime: <input name = "ime"  v-model ="ime"  type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		<label for = "organizacija">Oganizacija: </label>
				<select id = "organizacija"  class= "form-control"  v-model ="organizacija" required>
					<option v-for="o in organizacije" :value="o.ime">{{o.ime}}</option>
				</select>
       		</div>
       		<div class="form-group">
          		<label for = "kat">Kategorija: </label>   
				<select id = "kat" @change="onChange($event)" class= "form-control"  v-model ="kategorija" required>
					<option v-for="k in svekategorije" :value="k.ime">{{k.ime}}</option>
				</select>
       		</div>
       		<div class="form-group">
          		Broj jezgara: <input name = "broj" v-model ="broj" type="text" class="form-control" disabled>
       		</div>
       		<div class="form-group">
          		Ram: <input name = "ram" v-model ="ram" type="text" class="form-control" disabled>
       		</div>
       		<div class="form-group">
          		Gpu jezgra: <input name = "gpu" v-model ="gpu" type="text" class="form-control" disabled>
       		</div>
       		<div class="form-group">
           		<label for = "dis">Diskovi: (Drzite ctrl kada birate)</label>
				<select id = "dis" v-model="diskovi" multiple>
					 <option v-for="d in svidiskovi" :value="d.ime">{{d.ime}}</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button v-on:click.prevent = "dodaj()" class="btn btn-primary btn-block">Dodaj</button>     
       		</div>   
        </form>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Izmena kategorije</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        
      </div>
    </div>
  </div>
</div>
	</div>
`
	, 
	methods : {
		onChange(event) {
            var ime = event.target.value;
            console.log(ime);
            axios
		    .get('rest/getKategorija/' + ime)
		    .then((response) => {
		    	console.log(response.data);
		    	this.broj = response.data.brojJezgara
		    	this.ram = response.data.RAM;
		    	this.gpu = response.data.GPU;
		    });
            
		},
		ponistiFilter(){
        	console.log("ponisti");
        	this.filter.ime = "";
        	this.filter.ram = "";
        	this.filter.broj = "";
        	axios
		    .get('rest/getSveVM')
		    .then((response) => {
		    	console.log(response.data.virtualneMasine);
		    	this.vm = response.data.virtualneMasine;
		    });
        },
        proveri() {
        	console.log(this.diskovi);
        	if(this.ime == ""){
        		alert("Niste popunili ime!");
        		this.validacija = false;
        	}
        	else if(this.kategorija == ""){
        		alert("Niste popunili kategoriju!");
        		this.validacija = false;

        	}
        	else if(this.organizacija == ""){
        		alert("Niste popunili organizaciju!");
        		this.validacija = false;

        	}
        	else if(this.diskovi == ""){
        		alert("Niste popunili diskove!");
        		this.validacija = false;

        	}
        	else{
        		this.validacija = true;

        	}
        },
        dodaj() {
        	this.proveri();
        	if(this.validacija = false){
        		return;
        	}
        	axios
		    .post('rest/addVM', { "ime": this.ime,"organizacija": this.organizacija,"kategorija":this.kategorija,"brojJezgara": this.broj, "RAM": this.ram, "GPU": this.gpu, "diskovi":this.diskovi})
		    .then((response) => {
		    	console.log(response.data);
		    	$('#exampleModal').modal('hide');
            	$('.modal-backdrop').remove();
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
        },
        izmeni() {
        	this.proveri();
        	if(this.validacija = false){
        		return;
        	}
        	axios
		    .post('rest/izmeniKategorija',{  "staro": this.stara,"ime": this.ime, "broj": this.broj, "ram": this.ram, "gpu": this.gpu})
		    .then((response) => {
		    	$('#edit').modal('hide');
            	$('.modal-backdrop').remove();
		    	console.log("ok");
		    	console.log(response.data);
		    	axios
			    .get('rest/getSveKategorije')
			    .then((response) => {
			    	console.log(response.data.kategorije);
			    	this.kategorije = response.data.kategorije;
			    });
		    })
		    .catch(function(error){
				if(error.response){
					alert(error.response.data);
				};
        	});
        },
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
	computed: {
		filtriranevm: function(){
			console.log("filter");
			console.log(this.filter.broj);
			console.log(this.filter.ime);
			console.log(this.filter.ram);
			
			return this.vm.filter((v)=>{
				if(v.ime.toLowerCase().match(this.filter.ime.toLowerCase()) == null){
					return false;
				}
				if(this.filter.ram != "" && this.filter.ram != undefined){
					if(parseInt(v.RAM) < parseInt(this.filter.ram) ||
					parseInt(v.RAM) > 2*parseInt(this.filter.ram)){
						return false;
					}
				}
				if(this.filter.broj != "" && this.filter.broj != undefined){
					if(parseInt(v.brojJezgara) < parseInt(this.filter.broj) ||
					parseInt(v.brojJezgara) > 2*parseInt(this.filter.broj)){
						return false;
					}
				}
				return true;
				
			});
		},
		rezultati: function(){
			console.log(this.filtriranevm.length)
			console.log(this.filtriranevm.length == 0)
			if(this.filtriranevm.length == 0){
				console.log("nema nadjenih");
				//$("#rezultatiPretrage").html("Nema rezultata pretrage");
				return true;
			}
			//$("#rezultatiPretrage").html("");
			return false;

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
	    	axios
		    .get('rest/getOrganizacijaDiskovi')
		    .then((response) => {
		    	console.log(response.data.diskovi);
		    	this.svidiskovi = response.data.diskovi;
		    });
	    	axios
		    .get('rest/getSveOrganizacije')
		    .then((response) => {
		    	console.log(response.data);
		    	this.organizacije = response.data.organizacije;
		    });
	    	axios
		    .get('rest/getSveKategorije')
		    .then((response) => {
		    	console.log(response.data.kategorije);
		    	this.svekategorije = response.data.kategorije;
		    });
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});