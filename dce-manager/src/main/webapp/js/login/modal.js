function Modal(options) {
	$.extend(this, {
		title: '',
		model: 'dialog',
		time: 3000,
		type: 'info',
		content: '努力加载中...',
		cancelText: '取消',
		okText: '确定',		
		cancel: false,
		ok: false,
		auto: true,
		src: '',
		close: false,
		open: function(){},
		init:function(){}
	}, options || {});

	this.initialize();
};

Modal.prototype = {
	initialize: function() {
		var that = this,
			dialog;
		if (this.model == 'dialog' || this.model=='loading') {
			dialog = '<div class="modal">' +
				'  <div class="modal-dialog">' +
				'    {title}' +
				'    <div class="modal-bd">' +
				'      {content}' +
				'    </div>' +
				'    <div class="modal-footer">{cancel}{ok}</div>' +
				'  </div>' +
				'</div>';
				if(this.model=='loading'){
					this.ok = false;
					this.cancel=false;
					this.content = '<div class="modal-loading"></div><p>'+this.content+'</p>';
				}
			dialog = dialog.replace('{title}', this.title ? '<div class="modal-hd">' + this.title + '</div>' : '')
				.replace('{content}', this.content)
				.replace('{cancel}', this.cancel ? '<span class="modal-btn" data-btn="cancel">' + this.cancelText + '</span>' : '')
				.replace('{ok}', this.ok ? '<span class="modal-btn" data-btn="ok">' + this.okText + '</span>' : '');
		} else if (this.model == 'popup') {
			dialog = '<div style="top:'+(window.innerHeight-60)+'px" class="modal modal-popup">' +
				'  <div class="popup popup-' + this.type + '">' + this.content + '</div>' +
				'</div>';
		} else if (this.model == 'img') {
			dialog = '<div class="modal modal-img modal-active"><img src="'+this.src+'" /></div>';
		} else {
			return;
		}
		this.dialog = $(dialog);
		this.auto && this.show();
		this.init();
		this.events();
	},
	show: function() {
		var that = this;
		$(document.body).append(this.dialog);
		// if (!!(window.history && history.pushState)) {
		// 	history.pushState({
		// 		dialog: 1
		// 	}, "title", "?dialog");
		// 	window.onpopstate = function(e) {
		// 		that.remove();
		// 	}
		// }
		setTimeout(function() {
			that.dialog.addClass('modal-active');
		}, 10);
		if (this.model == 'popup') {
			setTimeout(function() {
				that.remove()
			}, this.time);
		}
		this.open.call(this);
	},
	setContent: function(val){
		this.dialog.find('.modal-bd').html(val);
	},
	remove: function() {
		var that = this;
		this.dialog.removeClass('modal-active');
		setTimeout(function() {
			try {
				that.close && that.close();
				that.dialog.remove();
			} catch (e) {}
		}, 310);
		// window.onpopstate = null;
	},
	events: function() {
		var that = this;
		this.dialog.delegate('.modal-btn', 'click', function() {
			switch ($(this).data('btn')) {
				case 'cancel':
					// if (typeof that.cancel == 'function' && that.cancel() !== false) that.remove();

					/*
						修改非回调函数才能关闭
					*/
					if (typeof that.cancel == 'function' && that.cancel() !== false) {
						that.remove()
					}else{
						that.remove()
					}
					break;
				case 'ok':
					// if (typeof that.ok == 'function' && that.ok() !== false) that.remove();
					/*
						修改非回调函数才能关闭
					*/
					if (typeof that.ok == 'function' && that.ok() !== false) {
						that.remove();
					}else{
						that.remove();
					}
					break;
			}
		});
		if(this.model == 'img'){
			this.dialog.on('click', function(){
				that.remove();
			});
		}
	}
}