Vue.component('diskovi',{
	data: function(){
		return {
			diskovi:[],
			organizacije:[],
			vm: [],
			tip: "",
			ime:"",
			kapacitet: "",
			virtuelnaMasina: "",
			organizacija: "",
			staro: "",
			superAdmin: false,
			admin: false,
			korisnik: false
		}
		
	},
	template: ` 
	<div id = "diskoviSAdmin" style="margin-left: 260px;">
		<h2 class="text-center">Diskovi</h2>
<table class="table table-striped" id = "diskovi">
  <thead>
    <tr>
      <th scope="col">Ime</th>
      <th scope="col">Kapacitet</th>
      <th scope="col">Virtuelna masina</th>
    </tr>
  </thead>
  <tbody>
		<tr v-for="d in diskovi">
			<td >{{d.ime}}</td>
			<td >{{d.kapacitet}}</td>
			<td >{{d.virtuelnaMasina}}</td>
			<td>
			<button v-if = "korisnik == false" type="button" v-on:click = "popuni(d)" class="btn btn-primary" data-toggle="modal" data-target="#edit">
				Izmeni
			</button>
			<button v-if = "korisnik == true" type="button" v-on:click = "popuni(d)" class="btn btn-primary" data-toggle="modal" data-target="#edit">
				Pregled
			</button>
			</td>
			<td>
			<button v-if = "superAdmin == true" type="button" v-on:click = "obrisi(d.ime)" class="btn btn-primary">
				Obrisi
			</button>
			</td>
		</tr>
  </tbody>
</table>
<!-- Button trigger modal -->
<button v-if = "korisnik == false" id = "Dodaj" v-on:click = "isprazniPolja()" type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Dodaj disk
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Dodavanje diska</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "dodajDisk">  
       		<div class="form-group">
          		Ime: <input name = "ime"  v-model ="ime"  type="text" class="form-control" >
       		</div>
       		<div class="form-group">
           		Tip diska: <select name = "tip"  v-model ="tip"  class="form-control" id="exampleFormControlSelect1">
          			<option value = "SSD">SSD</option>
      				<option value = "HDD">HDD</option>
				</select>
       		</div>
       		<div class="form-group">
          		Kapacitet: <select name = "kapacitet"  v-model ="kapacitet"  class="form-control" id="exampleFormControlSelect1">
          			<option value = "125">125</option>
      				<option value = "250">250</option>
					<option value = "500">500</option>
					<option value = "1000">1000</option>
					<option value = "2000">2000</option>
				</select>
       		</div>
       		<div class="form-group">
       			<label for = "organizacija">Oganizacija: </label>
				<select id = "organizacija"  class= "form-control"  v-model ="organizacija">
					<option v-for="o in organizacije" :value="o.ime">{{o.ime}}</option>
				</select>
		    </div>
       		<div class="form-group">
           		<label for = "vm">Virtuelna masina: </label>
				<select id = "vm"  class= "form-control"  v-model ="virtuelnaMasina">
					<option v-for="v in vm" :value="v.ime">{{v.ime}}</option>
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
        <h5 class="modal-title" id="exampleModalLabel">Izmena diska</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "izmeniDisk" v-if = "korisnik == false">  
       		<div class="form-group">
          		Ime: <input name = "ime"  v-model ="ime"  type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Tip diska: <select name = "tip"  v-model ="tip"  class="form-control" id="exampleFormControlSelect1">
          			<option value = "SSD">SSD</option>
      				<option value = "HDD">HDD</option>
				</select>
       		</div>
       		<div class="form-group">
          		Kapacitet: <select name = "kapacitet"  v-model ="kapacitet"  class="form-control" id="exampleFormControlSelect1">
          			<option value = "125">125</option>
      				<option value = "250">250</option>
					<option value = "500">500</option>
					<option value = "1000">1000</option>
					<option value = "2000">2000</option>
				</select>
       		</div>
       		<div class="form-group">
       			<label for = "organizacija">Oganizacija: </label>
				<select id = "organizacija"  class= "form-control"  v-model ="organizacija" required>
					<option v-for="o in organizacije" :value="o.ime">{{o.ime}}</option>
				</select>
		    </div>
       		<div class="form-group">
           		<label for = "vm">Virtuelna masina: </label>
				<select id = "vm"  class= "form-control"  v-model ="virtuelnaMasina">
					<option v-for="v in vm" :value="v.ime">{{v.ime}}</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button v-on:click.prevent = "izmeni()" class="btn btn-primary btn-block">Izmeni</button>
       		</div>   
        </form>
        <form id = "izmeniDisk" v-if= "korisnik == true">  
       		<div class="form-group">
          		Ime: <input name = "ime"  v-model ="ime"  type="text" class="form-control" disabled>
       		</div>
       		<div class="form-group">
           		Tip diska: <select name = "tip"  v-model ="tip"  class="form-control" id="exampleFormControlSelect1" disabled>
          			<option value = "SSD">SSD</option>
      				<option value = "HDD">HDD</option>
				</select>
       		</div>
       		<div class="form-group">
          		Kapacitet: <select name = "kapacitet"  v-model ="kapacitet"  class="form-control" id="exampleFormControlSelect1" disabled>
          			<option value = "125">125</option>
      				<option value = "250">250</option>
					<option value = "500">500</option>
					<option value = "1000">1000</option>
					<option value = "2000">2000</option>
				</select>
       		</div>
       		<div class="form-group">
       			<label for = "organizacija">Oganizacija: </label>
				<select id = "organizacija"  class= "form-control"  v-model ="organizacija" disabled>
					<option v-for="o in organizacije" :value="o.ime">{{o.ime}}</option>
				</select>
		    </div>
       		<div class="form-group">
           		<label for = "vm">Virtuelna masina: </label>
				<select id = "vm"  class= "form-control"  v-model ="virtuelnaMasina" disabled>
					<option v-for="v in vm" :value="v.ime">{{v.ime}}</option>
				</select>
       		</div> 
        </form>
      </div>
    </div>
  </div>
</div>
	</div>
`
	, 
	methods : {
        popuni(d) {
        	console.log("popuni");
        	this.ime = d.ime;
        	this.staro = d.ime;
        	this.kapacitet = d.kapacitet;
        	this.tip = d.tip;
        	this.organizacija = d.organizacija;
        	this.virtuelnaMasina = d.virtuelnaMasina;

        },
        isprazniPolja() {
        	this.ime = "";
        	this.staro = "";
        	this.kapacitet = "125";
        	this.tip = "HDD";
        	this.organizacija = "";
        	this.virtuelnaMasina = "";
        },
        dodaj() {
        	axios
		    .post('rest/addDisk', { "ime": this.ime, "tip": this.tip, "kapacitet": this.kapacitet, "organizacija": this.organizacija, "virtuelnaMasina": this.virtuelnaMasina})
		    .then((response) => {
		    	console.log(response.data);
		    	$('#exampleModal').modal('hide');
            	$('.modal-backdrop').remove();
            	axios
    		    .get('rest/getSviDiskovi')
    		    .then((response) => {
    		    	console.log(response.data.diskovi);
    		    	this.diskovi = response.data.diskovi;
    		    });
		    })
		    .catch(function(error){
				if(error.response){
					alert(error.response.data);
				};
        	});
        },
        izmeni() {
        	axios
		    .post('rest/izmeniDisk',{  "staro": this.staro,"ime": this.ime, "tip": this.tip, "kapacitet": this.kapacitet, "organizacija": this.organizacija, "virtuelnaMasina": this.virtuelnaMasina})
		    .then((response) => {
		    	$('#edit').modal('hide');
            	$('.modal-backdrop').remove();
		    	console.log("ok");
		    	console.log(response.data);
		    	axios
			    .get('rest/getSviDiskovi')
			    .then((response) => {
			    	console.log(response.data.diskovi);
			    	this.diskovi = response.data.diskovi;
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
		    .get('rest/obrisiDisk/' + ime )
		    .then((response) => {
		    	console.log(response.data);
		    	axios
			    .get('rest/getSviDiskovi')
			    .then((response) => {
			    	console.log(response.data.diskovi);
			    	this.diskovi = response.data.diskovi;
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
	    		this.Admin = true;
	    	}
	    	if(response.data.uloga == "KORISNIK"){
	    		this.korisnik = true;
	    	}
	    	axios
		    .get('rest/getSviDiskovi')
		    .then((response) => {
		    	console.log(response.data.diskovi);
		    	this.diskovi = response.data.diskovi;
		    });
	    	axios
		    .get('rest/getSveOrganizacije')
		    .then((response) => {
		    	console.log(response.data);
		    	this.organizacije = response.data.organizacije;
		    });
	    	axios
		    .get('rest/getSveVM')
		    .then((response) => {
		    	console.log(response.data);
		    	this.vm = response.data.virtualneMasine;
		    });
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});