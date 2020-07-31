Vue.component('organizacije',{
	data: function(){
		return {
			organizacije:[],
			staro: "",
			ime:"",
			opis: "",
			logo: ""
		}
		
	},
	template: ` 
	<div id = "organizacijeSAdmin" style="margin-left: 260px;">
		<h2 class="text-center">Organizacije</h2>
<table class="table table-striped" id = "organizacije">
  <thead>
    <tr>
      <th scope="col">Ime</th>
      <th scope="col">Opis</th>
      <th scope="col">Logo</th>
    </tr>
  </thead>
  <tbody>
		<tr v-for="o in organizacije">
			<td >{{o.ime}}</td>
			<td >{{o.opis}}</td>
			<td ><img v-bind:src="'/slike/' + o.logo" alt = "Logo"/></td>
			<td>
			<button type="button" v-on:click = "popuni(o)" class="btn btn-primary" data-toggle="modal" data-target="#edit">
				Izmeni
			</button>
			</td>
		</tr>
  </tbody>
</table>
<!-- Button trigger modal -->
<button id = "Dodaj" type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Dodaj organizaciju
</button>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Dodavanje organizacije</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "dodajOrganizacija">  
       		<div class="form-group">
          		Ime: <input name = "ime" v-model ="ime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Opis: <input name = "opis" v-model ="opis" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
				<label for="myfile">Odaberite sliku za logo: </label>
		        <input type="file" id="myfile" name="myfile"/>
           		<button   v-on:click.prevent = "upload()">Upload</button>
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
        <h5 class="modal-title" id="exampleModalLabel">Izmena organizacije</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "izmeniOrganizacija">  
       		<div class="form-group">
          		Ime: <input name = "ime" v-model ="ime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Opis: <input name = "ime" v-model ="opis" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
          		<label for="myfileIzmena">Odaberite sliku za logo: </label>
		        <input type="file" id="myfileIzmena" name="myfile"/>
           		<button   v-on:click.prevent = "uploadIzmena()">Upload</button>
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
		upload() {
			var formData = new FormData();
			var imagefile = document.querySelector('#myfile');
			console.log(imagefile);
			console.log(imagefile.files[0]);
			if(imagefile.files[0] == undefined){
				alert("Molimo vas da prvo odaberete sliku!");
				return;
			}
			formData.append("myfile", imagefile.files[0]);
			axios.post('/api/upload', formData, {
			    headers: {
			      'Content-Type': 'multipart/form-data'
			    }
			})
			.then((response) => {
		    	console.log(response.data);
		    	this.logo = imagefile.files[0].name;
		    	console.log(this.logo);
		    });
		  },
		  uploadIzmena() {
				var formData = new FormData();
				var imagefile = document.querySelector('#myfileIzmena');
				console.log(imagefile);
				console.log(imagefile.files[0]);
				if(imagefile.files[0] == undefined){
					alert("Molimo vas da prvo odaberete sliku!");
					return;
				}
				formData.append("myfileIzmena", imagefile.files[0]);
				axios.post('/api/uploadIzmena', formData, {
				    headers: {
				      'Content-Type': 'multipart/form-data'
				    }
				})
				.then((response) => {
    		    	console.log(response.data);
    		    	this.logo = imagefile.files[0].name;
    		    	console.log(this.logo);
    		    });
			  },
        popuni(o) {
        	console.log("popuni");
        	this.staro = o.ime;
        	this.ime = o.ime;
        	this.opis = o.opis;
        },
        isprazniPolja() {
        	this.staro = "";
        	this.ime = "";
        	this.opis = "";
        	this.logo = "";
        },
        dodaj() {
        	console
        	axios
		    .post('rest/addOrganizacija', { "ime": this.ime, "opis": this.opis, "logo": this.logo})
		    .then((response) => {
		    	console.log(response.data);
		    	$('#exampleModal').modal('hide');
            	$('.modal-backdrop').remove();
            	axios
    		    .get('rest/getSveOrganizacije')
    		    .then((response) => {
    		    	console.log(response.data);
    		    	this.organizacije = response.data.organizacije;
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
		    .post('rest/izmeniOrganizacija',{  "staro": this.staro, "ime": this.ime, "opis": this.opis, "logo": this.logo})
		    .then((response) => {
		    	$('#edit').modal('hide');
            	$('.modal-backdrop').remove();
		    	console.log("ok");
		    	console.log(response.data);
		    	axios
			    .get('rest/getSveOrganizacije')
			    .then((response) => {
			    	console.log(response.data);
			    	this.organizacije = response.data.organizacije;
			    });
		    })
		    .catch(function(error){
				if(error.response){
					alert(error.response.data);
				};
        	});;
        }
	},
	mounted() {
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
	    	console.log(response.data);
	    	axios
		    .get('rest/getSveOrganizacije')
		    .then((response) => {
		    	console.log(response.data);
		    	this.organizacije = response.data.organizacije;
		    });
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});