Vue.component('kategorije',{
	data: function(){
		return {
			kategorije:[],
			zaBrisanje: "",
			ime:"",
			broj: "",
			ram: "",
			gpu: "",
			stara: ""			
		}
		
	},
	template: ` 
	<div id = "kategorijeSAdmin" style="margin-left: 260px;">
		<h2 class="text-center">Kategorije</h2>
<table class="table table-striped" id = "kategorije">
  <thead>
    <tr>
      <th scope="col">Ime</th>
      <th scope="col">Broj jezgara</th>
      <th scope="col">RAM gB</th>
      <th scope="col">GPU jezgra</th>
    </tr>
  </thead>
  <tbody>
		<tr v-for="k in kategorije">
			<td >{{k.ime}}</td>
			<td >{{k.brojJezgara}}</td>
			<td >{{k.RAM}}</td>
			<td >{{k.GPU}}</td>
			<td>
			<button type="button" v-on:click = "popuni(k)" class="btn btn-primary" data-toggle="modal" data-target="#edit">
				Izmeni
			</button>
			</td>
			<td>
			<button type="button" v-on:click = "obrisi(k.ime)" class="btn btn-primary">
				Obrisi
			</button>
			</td>
		</tr>
  </tbody>
</table>
<!-- Button trigger modal -->
<button id = "Dodaj" v-on:click = "isprazniPolja()" type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Dodaj kategoriju
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Dodavanje kategorije</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "dodajKategorija">  
       		<div class="form-group">
          		Ime: <input name = "ime"  v-model ="ime"  type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Broj jezgara: <select name = "broj"  v-model ="broj"  class="form-control" id="exampleFormControlSelect1">
          			<option value = "1">1</option>
      				<option value = "2">2</option>
					<option value = "4">4</option>
					<option value = "8">8</option>
				</select>
       		</div>
       		<div class="form-group">
          		RAM:<select name = "ram"  v-model ="ram"  class="form-control" id="exampleFormControlSelect1">
          			<option value = "4">4</option>
      				<option value = "8">8</option>
					<option value = "16">16</option>
					<option value = "32">32</option>
					<option value = "64">64</option>
				</select>
       		</div>
       		<div class="form-group">
           		GPU: <select name = "gpu"  v-model ="gpu" class="form-control" id="exampleFormControlSelect1">
      				<option value = "0">0</option>
					<option value = "1">1</option>
					<option value = "2">2</option>
					<option value = "3">3</option>
					<option value = "4">4</option>
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
        <form id = "izmeniKategorija">  
       		<div class="form-group">
          		Ime: <input name = "ime" v-model ="ime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Broj jezgara: <select name = "broj" v-model ="broj" class="form-control" id="exampleFormControlSelect1">
          			<option>1</option>
      				<option>2</option>
					<option>4</option>
					<option>8</option>
				</select>
       		</div>
       		<div class="form-group">
          		RAM:<select name = "ram" v-model ="ram "class="form-control" id="exampleFormControlSelect1">
          			<option>4</option>
      				<option>8</option>
					<option>16</option>
					<option>32</option>
					<option>64</option>
				</select>
       		</div>
       		<div class="form-group">
           		GPU: <select name = "gpu" v-model ="gpu" class="form-control" id="exampleFormControlSelect1">
      				<option>0</option>
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button v-on:click.prevent = "izmeni()" class="btn btn-primary btn-block">Izmeni</button>
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
        popuni(k) {
        	console.log("popuni");
        	this.ime = k.ime;
        	this.broj = k.brojJezgara;
        	this.ram = k.RAM;
        	this.gpu = k.GPU;
        	this.stara = k.ime;

        },
        isprazniPolja() {
        	this.ime = "";
        	this.broj = "1";
        	this.ram = "4";
        	this.gpu = "0";
        },
        dodaj() {
        	axios
		    .post('rest/addKategorija', { "ime": this.ime, "brojJezgara": this.broj, "RAM": this.ram, "GPU": this.gpu})
		    .then((response) => {
		    	console.log(response.data);
		    	$('#exampleModal').modal('hide');
            	$('.modal-backdrop').remove();
            	axios
    		    .get('rest/getSveKategorije')
    		    .then((response) => {
    		    	console.log(response.data.kategorije);
    		    	this.kategorije = response.data.kategorije;
    		    });
		    });
        },
        izmeni() {
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
		    });
        },
        obrisi(ime){
        	console.log(ime);
        	axios
		    .get('rest/obrisiKategoriju/' + ime )
		    .then((response) => {
		    	console.log(response.data);
		    	axios
			    .get('rest/getSveKategorije')
			    .then((response) => {
			    	console.log(response.data.kategorije);
			    	this.kategorije = response.data.kategorije;
			    });
		    });
        }
        
        
	},
	mounted() {
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
	    	console.log(response.data);
	    	axios
		    .get('rest/getSveKategorije')
		    .then((response) => {
		    	console.log(response.data.kategorije);
		    	this.kategorije = response.data.kategorije;
		    });
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});