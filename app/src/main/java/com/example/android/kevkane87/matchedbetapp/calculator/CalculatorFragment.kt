package com.example.android.kevkane87.matchedbetapp.calculator

import android.annotation.SuppressLint
import android.app.*
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.Editable
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.kevkane87.matchedbetapp.Constants
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem
import com.example.android.kevkane87.matchedbetapp.R
import com.example.android.kevkane87.matchedbetapp.databinding.FragmentCalculatorBinding
import com.example.android.kevkane87.matchedbetapp.savedbets.AlarmReceiver

import java.util.*
import android.content.Intent.getIntent
import android.graphics.Color
import android.os.Build
import android.text.TextWatcher
import android.widget.RadioGroup
import androidx.core.widget.doAfterTextChanged


class CalculatorFragment: Fragment() {

    private val viewModel: CalculatorViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, CalculatorViewModelFactory(activity.application))
            .get(CalculatorViewModel::class.java)
    }

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private var dateTimeReminder = Calendar.getInstance()
    private var betId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCalculatorBinding>(
            inflater,
            R.layout.fragment_calculator, container, false
        )

        binding.calculatorViewModel = viewModel

        viewModel.clear()

        binding.lifecycleOwner = this

        createChannel(getString((R.string.bet_reminder_channel_id)),
            getString(R.string.bet_reminder_channel_name)
        )

        var existBundle = false

        //check for arguments or bundles passed via intent or navigation
        val extra = activity?.intent?.extras?.getBundle(Constants.REMINDER_ID)
        if (extra!=null){
            existBundle = true
        }
        else if(arguments != null){
            existBundle = true
        }

        //if bundle exists set bet data
        if (existBundle){

            viewModel.clear()

            var bundle = arguments
            if (bundle==null){
                bundle = extra
            }
            val betToDisplay = bundle?.getSerializable(Constants.REMINDER_ID) as MatchedBetDataItem?

            betId = betToDisplay!!.id

            if (betToDisplay.isSaved){
                binding.buttonDelete.isVisible = true
            }


            if (betToDisplay.bookiesStake == 0.0) {
                binding.backBetStake.text = Editable.Factory.getInstance().newEditable("10")
            }
            else{
                binding.backBetStake.text = Editable.Factory.getInstance().newEditable(betToDisplay.bookiesStake.toString())
            }
            binding.backBetOdds.text = Editable.Factory.getInstance().newEditable(betToDisplay.bookiesOdds.toString())
            binding.exCommission.text = Editable.Factory.getInstance().newEditable(betToDisplay.exchangeCommission.toString())
            binding.exLayBetOdds.text = Editable.Factory.getInstance().newEditable(betToDisplay.exchangeOdds.toString())


            viewModel.setBetDetails(betToDisplay)

            viewModel.setRadioButton()

            calculate(binding)
        }
        else{
            viewModel.clear()
            viewModel.setRadioButton()
        }

        binding.buttonCalculate.setOnClickListener{
            calculate(binding)
        }

        //save button
        binding.buttonSave.setOnClickListener {

            if (viewModel.backBetOdds.value != 0.0 && viewModel.backBetOdds.value != 0.0 && viewModel.layBetOdds.value != 0.0) {

                viewModel.setBetInfoDetails(binding.event.text.toString(),
                    binding.betOutcome.text.toString(),
                    binding.dateTime.text.toString(),
                    binding.bookmaker.text.toString(),
                    binding.exchange.text.toString())

                viewModel.saveBet()
                view?.findNavController()?.navigate(R.id.action_calculatorFragment_to_savedBetsFragment)
            }

            else{

                Toast.makeText(context,R.string.please_enter_bet_input_details,Toast.LENGTH_SHORT).show()
            }
        }

        //reminder button
        binding.buttonReminder.setOnClickListener{

            if (viewModel.backBetOdds.value != 0.0 && viewModel.backBetOdds.value != 0.0 && viewModel.layBetOdds.value != 0.0) {
                calculate(binding)
                setReminderDialog(viewModel.getMatchedBetDataItem())
            }
            else{
                Toast.makeText(context,R.string.please_enter_bet_input_details,Toast.LENGTH_SHORT).show()
            }
        }

        //delete button
        binding.buttonDelete.setOnClickListener{

            viewModel.deleteBet(betId)
            viewModel.clear()
            binding.backBetStake.text = Editable.Factory.getInstance().newEditable("")
            binding.backBetOdds.text = Editable.Factory.getInstance().newEditable("")
            binding.exCommission.text = Editable.Factory.getInstance().newEditable("")
            binding.exLayBetOdds.text = Editable.Factory.getInstance().newEditable("")
            Toast.makeText(context, R.string.bet_deleted_from_database, Toast.LENGTH_SHORT).show()
            binding.buttonDelete.isVisible=false

        }

        //copy button
        binding.buttonCopy.setOnClickListener(){

            var stake = binding.layStake.text
            stake = stake.drop(1)
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("copy_lay_stake", stake)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, stake.toString() + " copied to clipboard", Toast.LENGTH_SHORT).show()

        }

        //clear button
        binding.buttonClear.setOnClickListener(){

            viewModel.clear()
            binding.backBetStake.text = Editable.Factory.getInstance().newEditable("")
            binding.backBetOdds.text = Editable.Factory.getInstance().newEditable("")
            binding.exCommission.text = Editable.Factory.getInstance().newEditable("")
            binding.exLayBetOdds.text = Editable.Factory.getInstance().newEditable("")
            binding.buttonDelete.isVisible=false

        }

        //listeners to calculate for any user input changes

        binding.qualifier.setOnClickListener{
            calculate(binding)
        }
        binding.snr.setOnClickListener{
            calculate(binding)
        }
        binding.sr.setOnClickListener{
            calculate(binding)
        }

        binding.backBetStake.doAfterTextChanged {
            calculate(binding)
        }
        binding.backBetOdds.doAfterTextChanged {
            calculate(binding)
        }
        binding.exLayBetOdds.doAfterTextChanged {
            calculate(binding)
        }
        binding.exCommission.doAfterTextChanged {
            calculate(binding)
        }

        setHasOptionsMenu(true)

        return binding.root
    }


    //alarm manager to set for notification trigger
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(bet: MatchedBetDataItem){

        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //create intent and attach bet details for reminder
        val intent = Intent(context, AlarmReceiver::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Constants.REMINDER_ID, bet)
        intent.putExtra(Constants.REMINDER_ID, bundle)
        alarmIntent = intent.let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }

        alarmMgr?.set(
            AlarmManager.RTC_WAKEUP,
            dateTimeReminder.timeInMillis,
            alarmIntent
        )
        Toast.makeText(context, "Reminder is set", Toast.LENGTH_SHORT).show()
    }

    //dialog for setting reminder
    private fun setReminderDialog(bet: MatchedBetDataItem) {

        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder?.setMessage(R.string.set_reminder_for_bet)
                .setTitle(R.string.set_reminder)
            builder.apply {
                setPositiveButton(R.string.set_reminder
                ) { dialog, id ->
                    // User clicked set reminder button
                    setDateDialog(bet)
                    //setTimeDialog()
                }
                setNegativeButton(R.string.cancel
                ) { dialog, id ->
                    // User cancelled the dialog
                }
            }
            // Set other dialog properties

            // Create the AlertDialog
            builder.create().show()
        }
    }


    //get reminder date from user using dialog
    @SuppressLint("ResourceAsColor")
    private fun setDateDialog(bet: MatchedBetDataItem){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(),R.style.TimePickerTheme, { view, year, monthOfYear, dayOfMonth ->

            dateTimeReminder.set(Calendar.YEAR,year)
            dateTimeReminder.set(Calendar.MONTH,monthOfYear)
            dateTimeReminder.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            setTimeDialog(bet)

        }, year, month, day)
        dpd.show()
        dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.green_primary))
        dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.green_primary))

    }

    //get reminder time from user using dialog
    private fun setTimeDialog(bet: MatchedBetDataItem){

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            dateTimeReminder.set(Calendar.HOUR_OF_DAY,hour)
            dateTimeReminder.set(Calendar.MINUTE,minute)
            setAlarm(bet)
        }
        val tpd = TimePickerDialog(requireContext(), R.style.TimePickerTheme ,timeSetListener, cal.get(
            Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
        tpd.show()
        tpd.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.green_primary))
        tpd.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.green_primary))
    }


    //function to trigger calculation
    private fun calculate(binding: FragmentCalculatorBinding){

        viewModel.backBetStake.value =
            if (binding.backBetStake.text.isEmpty()) 0.0
            else binding.backBetStake.text.toString().toDouble()
        viewModel.backBetOdds.value =
            if (binding.backBetOdds.text.isEmpty()) 0.0
            else binding.backBetOdds.text.toString().toDouble()
        viewModel.layBetOdds.value =
            if (binding.exLayBetOdds.text.isEmpty()) 0.0
            else binding.exLayBetOdds.text.toString().toDouble()
        viewModel.exchangeCommission.value =
            if (binding.exCommission.text.isEmpty()) 0.0
            else binding.exCommission.text.toString().toDouble()

        viewModel.setBetType()
        viewModel.calculate()
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setRadioButton()
        viewModel.calculate()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saved_bets -> findNavController().navigate(R.id.action_calculatorFragment_to_savedBetsFragment)
            R.id.find_bets -> findNavController().navigate(R.id.action_calculatorFragment_to_findBetsFragment)
            R.id.help -> findNavController().navigate(R.id.action_calculatorFragment_to_helpFragment)
        }
        return true
    }

    //function to create notification channel using NotificationManager
    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.bet_reminder_channel_name)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
