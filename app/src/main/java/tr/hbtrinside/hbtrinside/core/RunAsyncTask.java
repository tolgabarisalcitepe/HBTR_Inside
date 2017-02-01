package tr.hbtrinside.hbtrinside.core;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RunAsyncTask extends AsyncTask<Runnable, Void, Void>
	{
		private Activity m_Act;
		private ProgressDialog m_PRDialog;
		private Dialog m_Dialog;

		public RunAsyncTask(Activity activity)
			{
				this.m_Act = activity;
			}

		protected void onPreExecute()
			{
				super.onPreExecute();
				if (this.m_Act != null)
					{
						View vw = m_Act.getWindow().getDecorView().getRootView();
						vw.setEnabled(false);
						m_Dialog = new Dialog(m_Act);
						m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						//TODO progressbar dialog tanımla
						m_Dialog.setContentView(null);

						m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
						//TODO progressbar  tanımla ve text box tanımla
						ProgressBar mProgress = (ProgressBar) m_Dialog.findViewById(0);
						TextView text1 = (TextView) m_Dialog.findViewById(0);
						text1.setText("Güncelleniyor, Lütfen Bekleyiniz");
						m_Dialog.setCancelable(false);
						m_Dialog.show();
						m_Dialog.setCanceledOnTouchOutside(false);

						//
						// m_PRDialog = new ProgressDialog(m_Act,
						// ProgressDialog.THEME_HOLO_LIGHT);
						// m_PRDialog.setIndeterminate(true);
						// m_PRDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
						// m_PRDialog.setCancelable(false);
						// //
						// m_PRDialog.setMessage("Veriler haz�rlan�yor. L�tfen bekleyin.");
						// m_PRDialog=ProgressDialog.show(m_Act, "",
						// "G�ncelleniyor, L�tfen Bekleyiniz.", false, true);

					}
			}

		@Override
		protected Void doInBackground(final Runnable... runnables)
			{
				// Progress dialog a��labilmesi i�in s�re tan�mak gerekiyor.
				// Sleep kald�r�l�rsa, UIThread bloke edildi�i i�in progress
				// dialog
				// hi� a��lm�yor.
				// SystemClock.sleep(500);
				m_Act.runOnUiThread(new Runnable()
					{
						String sync = "";
						@Override
						public void run()
							{
								try
									{
										for (int i = 0; i < runnables.length; i++)
											{
												runnables[i].run();
											}
									}
								catch (Exception ex)
									{
										ex.printStackTrace();
									}
							}
					});
				return null;
			}
		protected void onPostExecute(Void result)
			{
				if (m_Dialog != null && m_Dialog.isShowing())
					{
						try
							{
								View vw = m_Act.getWindow().getDecorView().getRootView();
								vw.setEnabled(true);
								m_Dialog.cancel();
							}
						catch (Exception ex)
							{
								ex.printStackTrace();
							}
						try
							{
								View vw = m_Act.getWindow().getDecorView().getRootView();
								vw.setEnabled(true);
								m_Dialog.dismiss();
							}
						catch (Exception ex)
							{
								ex.printStackTrace();
							}

					}
			}
	}
